package com.netcracker.crm.service;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.request.OrderRowRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Karpunets
 * @since 03.05.2017
 */
public interface OrderService {

    public List<Order> findByCustomerId(Long id);

    public List<Order> findOrgOrdersByCustId(Long id);

    Map<String, Object> getOrdersRow(OrderRowRequest orderRowRequest) throws IOException;

}
