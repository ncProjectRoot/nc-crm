package com.netcracker.crm.service;

import com.netcracker.crm.domain.model.Order;

import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 03.05.2017
 */
public interface OrderService {

    public List<Order> findByCustomerId(Long id);

}
