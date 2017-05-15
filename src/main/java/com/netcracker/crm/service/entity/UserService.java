package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.domain.request.UserRowRequest;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.exception.RegistrationException;

import java.util.List;
import java.util.Map;

/**
 * Created by bpogo on 4/30/2017.
 */
public interface UserService {
    Long createUser(UserDto userDto) throws RegistrationException;

    boolean activateUser(String userToken);

    Map<String, Object> getUsers(UserRowRequest userRowRequest, User user, boolean individual);

    List<String> getUserLastNamesByPattern(String lastName, User user);
}
