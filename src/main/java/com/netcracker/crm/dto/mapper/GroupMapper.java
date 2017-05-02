package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.dto.GroupDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

/**
 * Created by Pasha on 02.05.2017.
 */
public class GroupMapper extends PropertyMap<GroupDto, Group> {
    private ModelMapper discountModelMapper = new ModelMapper();

    public GroupMapper(){
        discountModelMapper.addMappings(new DiscountMapper());
    }

    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setName(source.getName());
    }
}
