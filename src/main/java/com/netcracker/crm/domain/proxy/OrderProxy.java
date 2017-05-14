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

    private Long customerId;
    private Long productId;
    private Long csrId;

    private UserDao userDao;
    private ProductDao productDao;

    public OrderProxy(UserDao userDao, ProductDao productDao) {
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public User getCustomer() {
        if (super.getCustomer() == null && customerId != null) {
            super.setCustomer(userDao.findById(customerId));
        }
        return super.getCustomer();
    }

    @Override
    public Product getProduct() {
        if (super.getProduct() == null && productId != null) {
            super.setProduct(productDao.findById(productId));
        }
        return super.getProduct();
    }

    @Override
    public User getCsr() {
        if (super.getCsr() == null && csrId != null) {
            super.setCsr(userDao.findById(csrId));
        }
        return super.getCsr();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCsrId() {
        return csrId;
    }

    public void setCsrId(Long csrId) {
        this.csrId = csrId;
    }
}
