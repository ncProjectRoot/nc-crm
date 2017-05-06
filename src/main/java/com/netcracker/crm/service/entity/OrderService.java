package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Order;

import java.util.List;
import com.netcracker.crm.domain.OrderRowRequest;
import com.netcracker.crm.dto.OrderDto;

import java.io.IOException;
import java.util.Map;

/**
 * @author Karpunets
 * @since 03.05.2017
 */
public interface OrderService {

    Order persist(OrderDto orderDto);
    public List<Order> findByCustomerId(Long id);

    Map<String, Object> getOrderRow(OrderRowRequest orderRowRequest) throws IOException;

}
