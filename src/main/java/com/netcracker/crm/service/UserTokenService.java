package com.netcracker.crm.service;

import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.domain.model.User;

/**
 * Created by Pasha on 02.05.2017.
 */
public interface UserTokenService {


    UserToken createUserToken(User user);

    UserToken getExistUserToken(Long userId, String token);

    boolean useToken(UserToken userToken);
}
