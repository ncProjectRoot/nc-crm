package com.netcracker.crm.controller.rest;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.exception.RegistrationException;
import com.netcracker.crm.service.entity.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bpogo on 4/30/2017.
 */
@RestController
@RequestMapping("/user")
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(UserDto userDto) throws RegistrationException {
        Long userId = userService.createUser(userDto);

        if (userId > 0) {
            log.info("User with id: " + userId + " successful created.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        log.error("User was not created.");

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
