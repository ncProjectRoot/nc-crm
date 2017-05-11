package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.UserRowRequest;

import java.util.List;

/**
 * Created by bpogo on 4/22/2017.
 */
public interface UserDao extends CrudDao<User> {

    User findByEmail(String email);

    long updatePassword(User user, String password);

    Long getUserRowsCount(UserRowRequest userRowRequest);

    List<User> findUsers(UserRowRequest userRowRequest);

    List<String> findUserLastNamesByPattern(String pattern);
}
