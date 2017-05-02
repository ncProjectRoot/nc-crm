package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.ProductStatus;
import com.netcracker.crm.dto.ProductDto;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * Created by Pasha on 30.04.2017.
 */
@Component
public class ProductMapper extends PropertyMap<ProductDto, Product>{
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setTitle(source.getTitle());
        map().setDescription(source.getDescription());
        map().setDefaultPrice(source.getDefaultPrice());
        if (source.getStatusName() != null){
            map().setStatus(ProductStatus.valueOf(source.getStatusName()));
        }
    }
}
