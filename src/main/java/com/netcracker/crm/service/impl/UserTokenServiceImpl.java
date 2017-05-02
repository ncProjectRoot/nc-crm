package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.dao.UserTokenDao;
import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.service.UserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Pasha on 02.05.2017.
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {
    private static final Logger log = LoggerFactory.getLogger(UserTokenServiceImpl.class);

    private final UserTokenDao userTokenDao;
    private final UserDao userDao;

    @Autowired
    public UserTokenServiceImpl(UserTokenDao userTokenDao, UserDao userDao) {
        this.userTokenDao = userTokenDao;
        this.userDao = userDao;
    }

    @Override
    public UserToken createUserToken(User user) {
        UserToken userToken = new UserToken();
        userToken.setToken(generateToken());
        userToken.setUsed(false);
        userToken.setUserMail(user.getEmail());
        userToken.setSendDate(LocalDateTime.now());
        userTokenDao.create(userToken);
        return userToken;
    }


    @Override
    public UserToken getExistUserToken(Long userId, String token) {
        User user = userDao.findById(userId);
        UserToken userToken = userTokenDao.finByUserEmail(user.getEmail());
        if (userToken.getToken().equals(token)){
            return userToken;
        }
        log.error("User token for user with userId = " + userId + " is not exist");
        return null;
    }


    @Override
    public boolean useToken(UserToken userToken) {
        if (userToken.getSendDate().plusDays(7).isAfter(LocalDateTime.now())){
            userToken.setUsed(true);
            userTokenDao.update(userToken);
            return true;
        }
        log.error("User token for user with user email = " + userToken.getUserMail() + " is not valid");
        return false;
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
