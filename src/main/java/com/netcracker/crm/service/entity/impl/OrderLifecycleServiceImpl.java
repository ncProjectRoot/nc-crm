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
        History history = order.getState().newOrder();

        return saveCondition(order, history);
    }

    @Override
    @Transactional
    public boolean processOrder(Long orderId, Long csrId) {
        if (orderId == null || csrId == null) return false;

        Order order = orderDao.findById(orderId);
        User csr = userDao.findById(csrId);
        if (csr != null || order != null) {
            order.setCsr(csr);
            History history = order.getState().processOrder();
            return saveCondition(order, history);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean activateOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        if (order != null) {
            History history = order.getState().activateOrder();
            return saveCondition(order, history);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean pauseOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        if (order != null) {
            History history = order.getState().pauseOrder();
            return saveCondition(order, history);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean resumeOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        if (order != null) {
            History history = order.getState().resumeOrder();
            return saveCondition(order, history);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean disableOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        if (order != null) {
            History history = order.getState().disableOrder();
            return saveCondition(order, history);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean requestToResumeOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        if (order != null) {
            History history = order.getState().requestToResumeOrder();
            return saveCondition(order, history);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean requestToPauseOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        if (order != null) {
            History history = order.getState().requestToPauseOrder();
            return saveCondition(order, history);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean requestToDisableOrder(Long orderId) {
        if (orderId == null) return false;

        Order order = orderDao.findById(orderId);
        if (order != null) {
            History history = order.getState().requestToDisableOrder();
            return saveCondition(order, history);
        }
        return false;
    }

    private Order convertFromDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        Product product = productDao.findById(orderDto.getProductId());
        User customer = userDao.findById(orderDto.getCustomerId());

        order.setProduct(product);
        order.setCustomer(customer);
        order.setDate(LocalDateTime.now());
        return order;
    }


    private boolean saveCondition(Order order, History history) {
        return historyDao.create(history) != null && orderDao.update(order) != null;
    }
}
