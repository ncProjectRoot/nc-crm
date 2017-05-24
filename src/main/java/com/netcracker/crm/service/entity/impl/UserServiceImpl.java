package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.dao.UserTokenDao;
import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.domain.real.RealUser;
import com.netcracker.crm.domain.request.UserRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.dto.mapper.ModelMapper;
import com.netcracker.crm.dto.mapper.impl.UserMapper;
import com.netcracker.crm.dto.row.UserRowDto;
import com.netcracker.crm.exception.RegistrationException;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.email.AbstractEmailSender;
import com.netcracker.crm.service.email.EmailParam;
import com.netcracker.crm.service.email.EmailParamKeys;
import com.netcracker.crm.service.email.EmailType;
import com.netcracker.crm.service.entity.UserService;
import com.netcracker.crm.service.security.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.core.session.SessionRegistry;
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

    @Resource
    private Environment env;

    private final UserDao userDao;
    private final UserTokenDao tokenDao;
    private final AbstractEmailSender emailSender;
    private final PasswordEncoder encoder;
    private final SessionRegistry sessionRegistry;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserTokenDao tokenDao, PasswordEncoder encoder,
                           @Qualifier("registrationSender") AbstractEmailSender emailSender,
                           SessionRegistry sessionRegistry, UserMapper userMapper) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
        this.emailSender = emailSender;
        this.encoder = encoder;
        this.sessionRegistry = sessionRegistry;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public Long createUser(UserDto userDto) throws RegistrationException {
        RandomString random = new RandomString(PASSWORD_LENGTH);
        String password = random.nextString();
        userDto.setPassword(password);
        encodePassword(userDto);

        User user = ModelMapper.map(userMapper.dtoToModelForCreate(), userDto, RealUser.class);

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
        User user = ModelMapper.map(userMapper.dtoToModel(), userDto, RealUser.class);
        userDao.update(user);
        return user;
    }

    @Override
    public User update(User user) {
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

        List<UserRowDto> dtoRows = ModelMapper.mapList(userMapper.modelToRowDto(), users, UserRowDto.class);
        response.put("rows", dtoRows);
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AutocompleteDto> getUserLastNamesByPattern(String pattern, User principal) {
        UserRole role = principal.getUserRole();
        List<User> users;
        if (role.equals(UserRole.ROLE_CUSTOMER) && principal.isContactPerson()) {
            users = userDao.findOrgUsersByPattern(pattern, principal);
        } else {
            users = userDao.findUsersByPattern(pattern);
        }
        return ModelMapper.mapList(userMapper.modelLastNameToAutocomplete(), users, AutocompleteDto.class);
    }

    public List<User> getOnlineCsrs() {
        List principals = sessionRegistry.getAllPrincipals();
        List<User> csrList = new ArrayList<>();
        for (Object o : principals) {
            if (o instanceof UserDetailsImpl) {
                User user = (User) o;
                if (user.getUserRole() == UserRole.ROLE_CSR) {
                    csrList.add(user);
                }
            }
        }
        return csrList;
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

    private void encodePassword(UserDto userDto) {
        String encodedPassword = encoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
    }
}
