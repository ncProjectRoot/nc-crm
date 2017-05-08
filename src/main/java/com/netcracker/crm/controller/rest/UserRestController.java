package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.UserRowRequest;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.exception.RegistrationException;
import com.netcracker.crm.service.entity.UserService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.controller.message.MessageHeader.ERROR_MESSAGE;
import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_SERVER_ERROR;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_USER_CREATED;

/**
 * Created by bpogo on 4/30/2017.
 */
@RestController
@RequestMapping("/users")
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;
    private final UserValidator userValidator;
    private final BindingResultHandler bindingResultHandler;
    private final ResponseGenerator<User> generator;

    @Autowired
    public UserRestController(UserService userService, UserValidator userValidator,
                              BindingResultHandler bindingResultHandler, ResponseGenerator generator) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.bindingResultHandler = bindingResultHandler;
        this.generator = generator;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<?> registerUser(@Valid UserDto userDto, BindingResult bindingResult) throws RegistrationException {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Validation form error.");
            return bindingResultHandler.handle(bindingResult);
        }

        Long userId = userService.createUser(userDto);

        if (userId > 0) {
            log.info("User with id: " + userId + " successful created.");
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_USER_CREATED, HttpStatus.CREATED);
        }

        log.error("User was not created.");
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers(UserRowRequest userRowRequest) {
        return new ResponseEntity<>(userService.getUsers(userRowRequest), HttpStatus.OK);
    }

    @GetMapping("/lastNames")
    public List<String> getLastNames(String pattern) {
        return userService.getUserLastNamesByPattern(pattern);
    }

}
