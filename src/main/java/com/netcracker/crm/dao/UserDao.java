package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.User;

/**
 * Created by bpogo on 4/22/2017.
 */
public interface UserDao extends CrudDao<User> {

    User findByEmail(String email);

    long updatePassword(User user, String password);
}
