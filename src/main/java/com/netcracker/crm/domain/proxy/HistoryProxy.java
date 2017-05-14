package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.Product;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class HistoryProxy extends History {

    private Long orderId;
    private Long complaintId;
    private Long productId;

    private OrderDao orderDao;
    private ComplaintDao complaintDao;
    private ProductDao productDao;

    public HistoryProxy(OrderDao orderDao, ComplaintDao complaintDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.complaintDao = complaintDao;
        this.productDao = productDao;
    }

    @Override
    public Order getOrder() {
        if (super.getOrder() == null && orderId != null) {
            super.setOrder(orderDao.findById(orderId));
        }
        return super.getOrder();
    }

    @Override
    public Complaint getComplaint() {
        if (super.getComplaint() == null && complaintId != null) {
            super.setComplaint(complaintDao.findById(complaintId));
        }
        return super.getComplaint();
    }

    @Override
    public Product getProduct() {
        if (super.getProduct() == null && productId != null) {
            super.setProduct(productDao.findById(productId));
        }
        return super.getProduct();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
