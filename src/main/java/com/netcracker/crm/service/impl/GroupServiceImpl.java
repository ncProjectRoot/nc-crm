package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.dto.GroupDto;
import com.netcracker.crm.dto.mapper.DiscountMapper;
import com.netcracker.crm.dto.mapper.GroupMapper;
import com.netcracker.crm.service.GroupService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Pasha on 01.05.2017.
 */
@Service
public class GroupServiceImpl implements GroupService{
    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupDao groupDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao) {
        this.groupDao = groupDao;
    }


    @Override
    public Group persist(GroupDto groupDto) {
        Group group = convertToEntity(groupDto);
        groupDao.create(group);
        return group;
    }

    @Override
    public List<Group> groupsByName(String name) {
        return groupDao.findByName(name);
    }

    private Group convertToEntity(GroupDto groupDto) {
        ModelMapper mapper = configureMapper();
        Discount discount = new Discount();
        if (groupDto.getDiscountId() > 0) {
            discount.setId(groupDto.getDiscountId());
        }

        Group group = mapper.map(groupDto, Group.class);

        group.setDiscount(discount);

        return group;
    }


    private ModelMapper configureMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new GroupMapper());

        return modelMapper;
    }
}
