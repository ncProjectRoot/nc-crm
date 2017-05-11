package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.exception.RegistrationException;

/**
 * Created by bpogo on 4/30/2017.
 */
public interface UserService {
    Long createUser(UserDto userDto) throws RegistrationException;

    boolean activateUser(String userToken);
    
    Long update(UserDto userDto);
    
    User findByEmail(String email);
}
