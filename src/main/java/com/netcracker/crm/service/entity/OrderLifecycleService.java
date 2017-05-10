package com.netcracker.crm.service.entity;

import com.netcracker.crm.dto.OrderDto;

/**
 * Created by bpogo on 5/10/2017.
 */
public interface OrderLifecycleService {

    boolean createOrder(OrderDto orderDto);

    boolean acceptOrder(Long orderId);

    boolean activateOrder(Long orderId);

    boolean pauseOrder(Long orderId);

    boolean resumeOrder(Long orderId);

    boolean cancelOrder(Long orderId);
}
