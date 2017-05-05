package com.netcracker.crm.datagenerator.impl.user;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 05.05.2017.
 */
public abstract class AbstractUserSetter extends AbstractSetter<User> {

    private int counter;
    private String password = "$2a$10$mJfq5rmvQR66o1xBN2xMzeptwYaxogOToWzvbVUeEHol.pe/jABia";

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> generate(int numbers) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < numbers; i++) {
            User user = generateObject();
            userDao.create(user);
            users.add(user);
        }

        return users;
    }

    @Override
    public User generateObject() {
        User user = new User();
        user.setAccountNonLocked(true);
        user.setEnable(true);
        user.setFirstName("John " + counter);
        user.setMiddleName("Middle john " + counter);
        user.setLastName("Doe " + counter);
        user.setAddress(null); // TODO mock address
        user.setPhone("0000000000");
        user.setUserRole(getRole());
        setContactPerson(user, counter);
        setOrganization(user);
        user.setEmail(getEmail(counter));
        user.setPassword(password);
        counter++;
        return user;
    }

    protected abstract UserRole getRole();
    protected abstract String getEmail(int counter);

    protected abstract void setOrganization(User user);
    protected abstract void setContactPerson(User user, int counter);



}
