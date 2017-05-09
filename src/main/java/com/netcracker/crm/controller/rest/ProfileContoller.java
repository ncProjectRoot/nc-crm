/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.controller.rest;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author YARUS
 */
@RestController
public class ProfileContoller {

    @Autowired
    UserDao userDao;
    
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public void updateUser(User user) {

    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public User getUser() {
        //return "Vasa pupkin";
        return userDao.findByEmail(getCurrentUsername());
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
