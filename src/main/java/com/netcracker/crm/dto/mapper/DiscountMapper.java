package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.dto.DiscountDto;
import org.modelmapper.PropertyMap;

/**
 * Created by Pasha on 02.05.2017.
 */
public class DiscountMapper extends PropertyMap<DiscountDto, Discount> {

    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setTitle(source.getTitle());
        map().setActive(source.getActive());
        map().setPercentage(source.getPercentage());
        map().setDescription(source.getDescription());
    }
}
