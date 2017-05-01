package com.netcracker.crm.service;

import com.netcracker.crm.dto.UserDto;

/**
 * Created by bpogo on 4/30/2017.
 */
public interface UserService {
    Long createUser(UserDto userDto);
}
