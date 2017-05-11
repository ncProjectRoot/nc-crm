package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.OrderDto;

import java.util.List;
import java.util.Map;

/**
 * @author Karpunets
 * @since 03.05.2017
 */
public interface OrderService {

    List<Order> findByCustomer(User customer);

    Order persist(OrderDto orderDto);

    List<Order> findByCustomerId(Long id);

    Map<String, Object> getOrdersRow(OrderRowRequest orderRowRequest);

    Order getOrderById(Long id);

    boolean hasCustomerProduct(Long productId, Long customerId);

    List<AutocompleteDto> getAutocompleteOrder(String pattern, User user);

}
