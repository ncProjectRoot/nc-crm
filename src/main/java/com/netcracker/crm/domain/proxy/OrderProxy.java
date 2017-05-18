package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.User;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class OrderProxy extends Order {

    private long customerId;
    private long productId;
    private long csrId;

    private UserDao userDao;
    private ProductDao productDao;

    public OrderProxy(UserDao userDao, ProductDao productDao) {
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public User getCustomer() {
        if (super.getCustomer() == null && customerId != 0) {
            super.setCustomer(userDao.findById(customerId));
        }
        return super.getCustomer();
    }

    @Override
    public Product getProduct() {
        if (super.getProduct() == null && productId != 0) {
            super.setProduct(productDao.findById(productId));
        }
        return super.getProduct();
    }

    @Override
    public User getCsr() {
        if (super.getCsr() == null && csrId != 0) {
            super.setCsr(userDao.findById(csrId));
        }
        return super.getCsr();
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCsrId() {
        return csrId;
    }

    public void setCsrId(long csrId) {
        this.csrId = csrId;
    }
}
