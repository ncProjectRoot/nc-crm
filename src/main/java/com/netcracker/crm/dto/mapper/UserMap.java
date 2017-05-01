package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.dto.UserDto;
import org.modelmapper.PropertyMap;

/**
 * Created by bpogo on 5/1/2017.
 */
public class UserMap extends PropertyMap<UserDto, User> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setFirstName(source.getFirstName());
        map().setMiddleName(source.getMiddleName());
        map().setLastName(source.getLastName());
        map().setEmail(source.getEmail());
        map().setPhone(source.getPhone());
        map().setEnable(source.isEnable());
        map().setContactPerson(source.isContactPerson());
        map().setAccountNonLocked(source.isAccountNonLocked());
        if (source.getUserRole() != null) {
            map().setUserRole(UserRole.valueOf(source.getUserRole()));
        }
    }
}
