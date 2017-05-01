package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.domain.model.Region;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.dto.mapper.AddressMap;
import com.netcracker.crm.dto.mapper.OrganizationMap;
import com.netcracker.crm.dto.mapper.RegionMap;
import com.netcracker.crm.dto.mapper.UserMap;
import com.netcracker.crm.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bpogo on 4/30/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Long createUser(UserDto userDto) {
        User user = mapFromDto(userDto);

        userDao.create(user);

        return user.getId();
    }

    private User mapFromDto(UserDto userDto) {
        ModelMapper mapper = configureMapper();

        Region region = mapper.map(userDto.getAddress().getRegion(), Region.class);
        Address address = mapper.map(userDto.getAddress(), Address.class);
        Organization organization = mapper.map(userDto.getOrganization(), Organization.class);
        User user = mapper.map(userDto, User.class);

        address.setRegion(region);
        user.setOrganization(organization);
        user.setAddress(address);

        return user;
    }

    private ModelMapper configureMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new UserMap());
        mapper.addMappings(new AddressMap());
        mapper.addMappings(new OrganizationMap());
        mapper.addMappings(new RegionMap());

        return mapper;
    }
}
