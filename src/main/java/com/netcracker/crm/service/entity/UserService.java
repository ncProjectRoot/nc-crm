package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.exception.RegistrationException;

/**
 * Created by bpogo on 4/30/2017.
 */
public interface UserService {
    Long createUser(UserDto userDto) throws RegistrationException;

    boolean activateUser(String userToken);
}
