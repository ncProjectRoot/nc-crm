package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.dao.UserTokenDao;
import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.request.UserRowRequest;
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
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //TODO: activation link for production
    private static final String ACTIVATION_LINK_TEMPLATE = "http://localhost:8888/user/registration/confirm?token=" + TOKEN_WILD_CARD;

    private final UserDao userDao;
    private final UserTokenDao tokenDao;
    private final RegionDao regionDao;
    private final OrganizationDao organizationDao;
    private final AbstractEmailSender emailSender;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserTokenDao tokenDao, RegionDao regionDao,
                           OrganizationDao organizationDao, PasswordEncoder encoder,
                           @Qualifier("registrationSender") AbstractEmailSender emailSender) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
        this.regionDao = regionDao;
        this.organizationDao = organizationDao;
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
    public boolean updateUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        Long updateId = userDao.update(user);
        if (updateId > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUsers(UserRowRequest userRowRequest) {
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

    @Override
    public List<String> getUserLastNamesByPattern(String pattern) {
        return userDao.findUserLastNamesByPattern(pattern);
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

        String activationLink = ACTIVATION_LINK_TEMPLATE.replaceAll(TOKEN_WILD_CARD, token);
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
        User user = mapper.map(userDto, User.class);
        if (userDto.getId() == null) {
            user.setEnable(false);
        }
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
