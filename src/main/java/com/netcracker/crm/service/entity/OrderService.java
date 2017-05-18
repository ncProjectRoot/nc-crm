package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.GraphDto;
import com.netcracker.crm.dto.OrderDto;
import com.netcracker.crm.dto.OrderHistoryDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Karpunets
 * @since 03.05.2017
 */
public interface OrderService {

    Order create(OrderDto orderDto);

    Order getOrderById(Long id);

    Map<String, Object> getOrdersRow(OrderRowRequest orderRowRequest);
    List<AutocompleteDto> getAutocompleteOrder(String pattern, User user);

    boolean hasCustomerProduct(Long productId, Long customerId);
    List<Order> findByCustomer(User customer);

    GraphDto getStatisticalGraph(GraphDto graphDto);
    Set<OrderHistoryDto> getOrderHistory(Long id);

}
