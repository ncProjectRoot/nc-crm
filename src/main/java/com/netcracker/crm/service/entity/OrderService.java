package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.dto.*;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;
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

    List<OrderViewDto> getCsrActivateOrder(Long csrId);

    List<OrderViewDto> getCsrPauseOrder(Long csrId);

    List<OrderViewDto> getCsrResumeOrder(Long csrId);

    List<OrderViewDto> getCsrDisableOrder(Long csrId);

    Integer getCsrOrderCount(Long csrId);

    boolean checkAccessToOrder(User customer, Long orderId);

    void getPdfReport(Long orderId, HttpServletResponse response);
}
