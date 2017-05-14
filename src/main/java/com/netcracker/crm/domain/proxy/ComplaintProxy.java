package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class ComplaintProxy extends Complaint {

    private Long customerId;
    private Long pmgId;
    private Long orderId;

    private UserDao userDao;
    private OrderDao orderDao;

    public ComplaintProxy(UserDao userDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @Override
    public User getCustomer() {
        if (super.getCustomer() == null && customerId != null) {
            super.setCustomer(userDao.findById(customerId));
        }
        return super.getCustomer();
    }

    @Override
    public User getPmg() {
        if (super.getPmg() == null && pmgId != null) {
            super.setPmg(userDao.findById(pmgId));
        }
        return super.getPmg();
    }

    @Override
    public Order getOrder() {
        if (super.getOrder() == null && orderId != null) {
            super.setOrder(orderDao.findById(orderId));
        }
        return super.getOrder();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPmgId() {
        return pmgId;
    }

    public void setPmgId(Long pmgId) {
        this.pmgId = pmgId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
