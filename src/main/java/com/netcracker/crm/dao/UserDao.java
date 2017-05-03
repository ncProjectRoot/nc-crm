package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.User;

/**
 * Created by bpogo on 4/22/2017.
 */
public interface UserDao {
    long create(User user);

    long update(User user);

    long delete(Long id);

    User findById(Long id);

    User findByEmail(String email);

    long updatePassword(User user, String password);

    void setUserEnable(Long id, boolean enable);
}
