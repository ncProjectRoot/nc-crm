package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.dto.OrganizationDto;
import org.modelmapper.PropertyMap;

/**
 * Created by bpogo on 5/1/2017.
 */
public class OrganizationMap extends PropertyMap<OrganizationDto, Organization> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setName(source.getName());
    }
}
