package com.netcracker.crm.domain.model;

import java.time.LocalDateTime;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 24.04.2017
 */
public class Order {
    private Long id;
    private LocalDateTime date;
    private LocalDateTime preferedDate;
    private OrderStatus status;
    private User customer;
    private Product product;
    private User csr;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getCsr() {
        return csr;
    }

    public void setCsr(User csr) {
        this.csr = csr;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getPreferedDate() {
        return preferedDate;
    }

    public void setPreferedDate(LocalDateTime preferedDate) {
        this.preferedDate = preferedDate;
    }
}
