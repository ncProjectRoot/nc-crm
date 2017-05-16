package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.scheduler.OrderSchedulerSqlGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author YARUS
 */
public interface OrderDao extends CrudDao<Order> {
    List<Order> findAllByDateFinish(LocalDate date);

    List<Order> findAllByPreferredDate(LocalDate date);

    List<Order> findAllByPrefDateAndStatus(OrderSchedulerSqlGenerator generator, List<User> csrs,
                                           LocalDateTime to, OrderStatus orderStatus);
    List<Order> findAllByStatus(OrderSchedulerSqlGenerator generator, List<User> csrs,
                                           OrderStatus orderStatus);

    List<Order> findAllByProductId(Long id);

    List<Order> findAllByCustomerId(Long id);

    List<Order> findAllByCsrId(Long id);

    List<Order> findAllByCsrId(LocalDateTime to, OrderStatus orderStatus, Long id);

    List<Order> findAllByCsrId(OrderStatus orderStatus, Long id);

    List<Order> findAllByCsrIdAndCustomerIdBetweenDates(Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

    List<Order> findOrdersByCsrIdAndArrayOfCustomerIdBetweenDates(Long csr_id, List<Long> customer_id_list, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

    List<Order> findOrdersByCsrIdBetweenDates(Long csr_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

    List<Order> findOrderRows(OrderRowRequest orderRowRequest);

    Long getOrderRowsCount(OrderRowRequest orderRowRequest);

    List<Order> findOrgOrdersByIdOrTitle(String pattern, Long customerId);

    List<Order> findByIdOrTitleByCustomer(String pattern, Long customerId);

    Boolean hasCustomerProduct(Long productId, Long customerId);
}
