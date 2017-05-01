package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Region;
import com.netcracker.crm.dto.RegionDto;
import org.modelmapper.PropertyMap;

/**
 * Created by bpogo on 5/1/2017.
 */
public class RegionMap extends PropertyMap<RegionDto, Region> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setName(source.getName());
    }
}
