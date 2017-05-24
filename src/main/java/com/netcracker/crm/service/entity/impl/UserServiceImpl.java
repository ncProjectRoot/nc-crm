package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.*;
import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.request.UserRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.dto.mapper.UserMap;
import com.netcracker.crm.dto.row.UserRowDto;
import com.netcracker.crm.exception.RegistrationException;
import com.netcracker.crm.service.email.AbstractEmailSender;
import com.netcracker.crm.service.email.EmailParam;
import com.netcracker.crm.service.email.EmailParamKeys;
import com.netcracker.crm.service.email.EmailType;
import com.netcracker.crm.service.entity.UserService;
import com.netcracker.crm.service.security.RandomString;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by bpogo on 4/30/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final int PASSWORD_LENGTH = 10;
    private static final String TOKEN_WILD_CARD = "%token%";
    private static final String LOCAL_ACTIVATION_LINK_TEMPLATE = "http://localhost:8888/user/registration/confirm?token=" + TOKEN_WILD_CARD;
    private static final String PRODUCTION_ACTIVATION_LINK_TEMPLATE = "http://nc-project.tk/user/registration/confirm?token=" + TOKEN_WILD_CARD;
    private static final String DEFAULT_AVATAR = "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png";

    @Resource
    private Environment env;

    private final UserDao userDao;
    private final UserTokenDao tokenDao;
    private final RegionDao regionDao;
    private final OrganizationDao organizationDao;
    private final AddressDao addressDao;
    private final AbstractEmailSender emailSender;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserTokenDao tokenDao, RegionDao regionDao,
                           OrganizationDao organizationDao, AddressDao addressDao, PasswordEncoder encoder,
                           @Qualifier("registrationSender") AbstractEmailSender emailSender) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
        this.regionDao = regionDao;
        this.organizationDao = organizationDao;
        this.addressDao = addressDao;
        this.emailSender = emailSender;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public Long createUser(UserDto userDto) throws RegistrationException {
        RandomString random = new RandomString(PASSWORD_LENGTH);
        String password = random.nextString();
        userDto.setPassword(password);
        encodePassword(userDto);

        User user = mapFromDto(userDto);

        userDao.create(user);
        String registrationToken = createUserRegistrationToken(user);
        sendRegistrationEmail(user, password, registrationToken);

        return user.getId();
    }

    @Override
    @Transactional
    public boolean activateUser(String token) {
        UserToken userToken = tokenDao.getUserToken(token);
        if (userToken != null && !userToken.isUsed()) {
            tokenDao.updateToken(token, true);
            User userToActivate = userDao.findById(userToken.getUser().getId());
            userToActivate.setEnable(true);
            userToActivate.setAccountNonLocked(true);
            userDao.update(userToActivate);
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User update(UserDto userDto) {
        User user = convertToEntity(userDto);
        userDao.update(user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUsers(UserRowRequest userRowRequest, User principal, boolean individual) {
        UserRole role = principal.getUserRole();
        if (role.equals(UserRole.ROLE_CUSTOMER) && principal.isContactPerson()) {
            userRowRequest.setCustomerId(principal.getId());
        }
        Map<String, Object> response = new HashMap<>();
        Long length = userDao.getUserRowsCount(userRowRequest);
        response.put("length", length);
        List<User> users = userDao.findUsers(userRowRequest);

        List<UserRowDto> dtoRows = new ArrayList<>();
        for (User user : users) {
            dtoRows.add(convertToRowDto(user));
        }
        response.put("rows", dtoRows);
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AutocompleteDto> getUserLastNamesByPattern(String pattern, User principal) {
        UserRole role = principal.getUserRole();
        List<String> names;
        if (role.equals(UserRole.ROLE_CUSTOMER) && principal.isContactPerson()) {
            names = userDao.findOrgUserLastNamesByPattern(pattern, principal);
        } else {
            names = userDao.findUserLastNamesByPattern(pattern);
        }
        List<AutocompleteDto> result = new ArrayList<>();
        for (String userLastName : names) {
            AutocompleteDto autocompleteDto = new AutocompleteDto();
            autocompleteDto.setValue(userLastName);
            result.add(autocompleteDto);
        }
        return result;
    }

    @Override
    @Transactional
    public String getAvatar(Long id) {
        User user = userDao.findById(id);
        Gravatar gravatar = new Gravatar();
        gravatar.setSize(500);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        if (user != null) {
            byte[] byteUrl = gravatar.download(user.getEmail());
            String url = gravatar.getUrl(user.getEmail());
            if (byteUrl != null) {
                return url;
            }
        }
        return DEFAULT_AVATAR;
    }

    private String createUserRegistrationToken(User user) {
        UserToken userToken = new UserToken();
        userToken.setToken(generateToken());
        userToken.setUsed(false);
        userToken.setUser(user);
        userToken.setSendDate(LocalDateTime.now());

        tokenDao.create(userToken);

        return userToken.getToken();
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void sendRegistrationEmail(User user, String password, String token) throws RegistrationException {
        EmailParam emailParam = new EmailParam(EmailType.REGISTRATION);

        String activationLink;
        if (env.acceptsProfiles("production")) {
            activationLink = PRODUCTION_ACTIVATION_LINK_TEMPLATE.replaceAll(TOKEN_WILD_CARD, token);
        } else {
            activationLink = LOCAL_ACTIVATION_LINK_TEMPLATE.replaceAll(TOKEN_WILD_CARD, token);
        }
        emailParam.put(EmailParamKeys.USER_REFERENCE, activationLink);
        emailParam.put(EmailParamKeys.USER, user);
        emailParam.put(EmailParamKeys.USER_PASSWORD, password);

        try {
            emailSender.send(emailParam);
            log.info("Registration email was sent to user with id: " + user.getId());
        } catch (MessagingException e) {
            throw new RegistrationException("Registration email wasn't sent.", e);
        }
    }

    private UserRowDto convertToRowDto(User user) {
        UserRowDto userRowDto = new UserRowDto();
        userRowDto.setId(user.getId());
        userRowDto.setFirstName(user.getFirstName());
        userRowDto.setMiddleName(user.getMiddleName());
        userRowDto.setLastName(user.getLastName());
        userRowDto.setEmail(user.getEmail());
        userRowDto.setPhone(user.getPhone());
        userRowDto.setContactPerson(user.isContactPerson());
        userRowDto.setUserRole(user.getUserRole().getFormattedName());
        userRowDto.setAccountNonLocked(user.isAccountNonLocked());

        Organization organization = user.getOrganization();
        if (organization != null) {
            userRowDto.setOrganizationName(organization.getName());
        }

        Address address = user.getAddress();
        if (address != null) {
            userRowDto.setFormattedAddress(address.getFormattedAddress());
        }

        return userRowDto;
    }

    private User mapFromDto(UserDto userDto) {
        ModelMapper mapper = configureMapper();
        User user = mapper.map(userDto, User.class);

        if (user.getUserRole().equals(UserRole.ROLE_CUSTOMER)) {
            Address address = new Address();
            address.setLatitude(userDto.getAddressLatitude());
            address.setLongitude(userDto.getAddressLongitude());
            address.setDetails(userDto.getAddressDetails());
            address.setFormattedAddress(userDto.getFormattedAddress());
            Region region = regionDao.findByName(userDto.getAddressRegionName());
            if (region == null) {
                region = new Region();
                region.setName(userDto.getAddressRegionName());
            }
            address.setRegion(region);
            user.setAddress(address);

            Organization organization = organizationDao.findByName(userDto.getOrganizationName());
            if (organization == null) {
                organization = new Organization();
                organization.setName(userDto.getOrganizationName());
            }
            user.setOrganization(organization);
        } else {
            user.setAddress(null);
            user.setOrganization(null);
        }
        return user;
    }

    private User convertToEntity(UserDto userDto) {
        ModelMapper mapper = configureMapper();

        Organization organization = userDto.getOrgId() > 0 ? organizationDao.findById(userDto.getOrgId()) : null;
        Address address = userDto.getAddressId() > 0 ? addressDao.findById(userDto.getAddressId()) : null;
        User user = mapper.map(userDto, User.class);

        user.setOrganization(organization);
        user.setAddress(address);

        return user;
    }

    private ModelMapper configureMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new UserMap());
        return mapper;
    }

    private void encodePassword(UserDto userDto) {
        String encodedPassword = encoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
    }
}
