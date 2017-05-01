package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 01.05.2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    private User userCreated;

    @Before
    public void create() throws Exception {
        userCreated = new User();
        userCreated.setPassword("test password");
        userCreated.setFirstName("test first name");
        userCreated.setMiddleName("test middle name");
        userCreated.setEmail("test email");
        userCreated.setEnable(false);
        userCreated.setAccountNonLocked(false);
        userCreated.setContactPerson(false);
        userCreated.setUserRole(UserRole.ROLE_CUSTOMER);

        userDao.create(userCreated);
        assertNotNull(userCreated.getId());
    }

    @Test
    public void findAndUpdateAndCount() throws Exception {
        User userFoundById = userDao.findById(userCreated.getId());
        assertEquals(userCreated.getFirstName(), userFoundById.getFirstName());

        User userFoundByName = userDao.findByEmail(userCreated.getEmail());
        assertEquals(userCreated.getId(), userFoundByName.getId());

        userCreated.setMiddleName("test update middle name");
        assertEquals(userDao.update(userCreated), userCreated.getId());
    }

    @After
    public void delete() throws Exception {
        long affectedRows = userDao.delete(userCreated);
        assertEquals(affectedRows, 1L);
    }

}