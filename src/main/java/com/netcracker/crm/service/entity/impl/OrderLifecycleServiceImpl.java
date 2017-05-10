package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.dto.OrderDto;
import com.netcracker.crm.service.entity.OrderLifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by bpogo on 5/10/2017.
 */
@Service
public class OrderLifecycleServiceImpl implements OrderLifecycleService {
    private final OrderDao orderDao;
    private final HistoryDao historyDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    @Autowired
    public OrderLifecycleServiceImpl(OrderDao orderDao, HistoryDao historyDao,
                                     ProductDao productDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.historyDao = historyDao;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Override
    public boolean createOrder(OrderDto orderDto) {
        Order order = convertFromDtoToEntity(orderDto);
        orderDao.create(order);
        return true;
    }

    @Override
    @Transactional
    public boolean acceptOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        History history = order.getState().acceptOrder();
        return save(order, history);
    }

    @Override
    @Transactional
    public boolean activateOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        History history = order.getState().activateOrder();
        return save(order, history);
    }

    @Override
    @Transactional
    public boolean pauseOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        History history = order.getState().pauseOrder();
        return save(order, history);
    }

    @Override
    @Transactional
    public boolean resumeOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        History history = order.getState().resumeOrder();
        return save(order, history);
    }

    @Override
    @Transactional
    public boolean cancelOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        History history = order.getState().cancelOrder();
        return save(order, history);
    }

    private Order convertFromDtoToEntity(OrderDto orderDto){
        Order order = new Order();
        Product product = productDao.findById(orderDto.getProductId());
        User customer = userDao.findById(orderDto.getCustomerId());

        order.setProduct(product);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.NEW);
        order.setDate(LocalDateTime.now());
        return order;
    }

    private boolean save(Order order, History history) {
        return historyDao.create(history) != null && orderDao.update(order) != null;
    }
}
