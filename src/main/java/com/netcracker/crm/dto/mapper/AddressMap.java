package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

/**
 * Created by bpogo on 5/1/2017.
 */
public class AddressMap extends PropertyMap<AddressDto, Address> {
    private ModelMapper regionModelMapper = new ModelMapper();

    public AddressMap() {
        regionModelMapper.addMappings(new RegionMap());
    }

    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setLatitude(source.getLatitude());
        map().setLongitude(source.getLongitude());
    }
}
