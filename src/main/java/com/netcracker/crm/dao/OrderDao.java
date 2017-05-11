package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.request.OrderRowRequest;

import java.time.LocalDate;
import java.util.List;

/**
 * @author YARUS
 */
public interface OrderDao extends CrudDao<Order> {
    List<Order> findAllByDateFinish(LocalDate date);

    List<Order> findAllByPreferredDate(LocalDate date);

    List<Order> findAllByProductId(Long id);

    List<Order> findAllByCustomerId(Long id);

    List<Order> findAllByCsrId(Long id);

    List<Order> findOrderRows(OrderRowRequest orderRowRequest);

    Long getOrderRowsCount(OrderRowRequest orderRowRequest);

    List<Order> findOrgOrdersByIdOrTitle(String pattern, Long customerId);

    List<Order> findByIdOrTitleByCustomer(String pattern, Long customerId);

    Boolean hasCustomerProduct(Long productId, Long customerId);
}
