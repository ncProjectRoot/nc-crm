package com.netcracker.crm.dao;

import com.netcracker.crm.domain.UserToken;

/**
 * Created by Pasha on 02.05.2017.
 */
public interface UserTokenDao {
    Long create(UserToken userToken);

    boolean updateToken(String token, boolean used);

    UserToken getUserToken(String token);
}
