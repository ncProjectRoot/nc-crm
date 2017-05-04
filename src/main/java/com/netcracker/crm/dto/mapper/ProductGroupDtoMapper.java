package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.dto.ProductGroupDto;
import org.modelmapper.PropertyMap;

/**
 * Created by Pasha on 03.05.2017.
 */
public class ProductGroupDtoMapper extends PropertyMap<Product, ProductGroupDto> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setTitle(source.getTitle());
    }
}
