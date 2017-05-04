package com.netcracker.crm.dto.mapper;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.dto.OrderRowDto;
import org.modelmapper.PropertyMap;

/**
 * @author Karpunets
 * @since 03.05.2017
 */
public class OrderRowMapper extends PropertyMap<OrderRowDto, Order> {

    @Override
    protected void configure() {

    }

}
