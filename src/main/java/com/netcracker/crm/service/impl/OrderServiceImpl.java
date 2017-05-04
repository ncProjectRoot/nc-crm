package com.netcracker.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.OrderRowRequest;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.dto.OrderRowDto;
import com.netcracker.crm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karpunets
 * @since 03.05.2017
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Map<String, Object> getOrderRow(OrderRowRequest orderRowRequest) throws IOException {
        Map<String, Object> response = new HashMap<>();
        Long length = orderDao.getOrderRowsCount(orderRowRequest);
        response.put("length", length);
        List<Order> orders = orderDao.findOrderRows(orderRowRequest);
        List<OrderRowDto> ordersDto = new ArrayList<>();
        for (Order order : orders) {
            OrderRowDto orderRowDto = new OrderRowDto();
            orderRowDto.setId(order.getId());
            orderRowDto.setStatus(order.getStatus().getName());
            orderRowDto.setProductId(order.getProduct().getId());
            orderRowDto.setProductTitle(order.getProduct().getTitle());
            orderRowDto.setProductStatus(order.getProduct().getStatus().getName());
            orderRowDto.setCustomer(order.getCustomer().getId());
            if (order.getCsr() != null) {
                orderRowDto.setCsr(order.getCsr().getId());
            }
            orderRowDto.setDateFinish(order.getDate().toString());
            orderRowDto.setPreferredDate(order.getPreferedDate().toString());
            ordersDto.add(orderRowDto);
        }
        response.put("orders", ordersDto);
        return response;
    }
}
