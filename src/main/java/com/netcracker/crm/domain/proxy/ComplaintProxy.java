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

    private long customerId;
    private long pmgId;
    private long orderId;

    private UserDao userDao;
    private OrderDao orderDao;

    public ComplaintProxy(UserDao userDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @Override
    public User getCustomer() {
        if (super.getCustomer() == null && customerId != 0) {
            super.setCustomer(userDao.findById(customerId));
        }
        return super.getCustomer();
    }

    @Override
    public User getPmg() {
        if (super.getPmg() == null && pmgId != 0) {
            super.setPmg(userDao.findById(pmgId));
        }
        return super.getPmg();
    }

    @Override
    public Order getOrder() {
        if (super.getOrder() == null && orderId != 0) {
            super.setOrder(orderDao.findById(orderId));
        }
        return super.getOrder();
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getPmgId() {
        return pmgId;
    }

    public void setPmgId(long pmgId) {
        this.pmgId = pmgId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
