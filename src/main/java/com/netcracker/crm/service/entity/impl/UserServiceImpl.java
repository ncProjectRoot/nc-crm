package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.dao.UserTokenDao;
import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.dto.mapper.UserMap;
import com.netcracker.crm.exception.RegistrationException;
import com.netcracker.crm.service.entity.UserService;
import com.netcracker.crm.service.email.AbstractEmailSender;
import com.netcracker.crm.service.email.EmailParam;
import com.netcracker.crm.service.email.EmailParamKeys;
import com.netcracker.crm.service.email.EmailType;
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
import java.util.UUID;

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
//        sendRegistrationEmail(user, password, registrationToken);

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

    private User mapFromDto(UserDto userDto) {
        ModelMapper mapper = configureMapper();
        User user = mapper.map(userDto, User.class);

        if (user.getUserRole().equals(UserRole.ROLE_CUSTOMER)) {
            Address address = new Address();
            address.setLatitude(userDto.getAddressLatitude());
            address.setLongitude(userDto.getAddressLongitude());
            address.setDetails(userDto.getAddressDetails());
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
