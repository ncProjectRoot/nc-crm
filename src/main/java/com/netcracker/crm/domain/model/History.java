package com.netcracker.crm.domain.model;

import java.time.LocalDateTime;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 24.04.2017
 */
public class History {
    private Long id;
    private Status newStatus;
    private LocalDateTime dateChangeStatus;
    private String descChangeStatus;
    private Order order;
    private Complaint complaint;
    private Product product;

    public History() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Status newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getDateChangeStatus() {
        return dateChangeStatus;
    }

    public void setDateChangeStatus(LocalDateTime dateChangeStatus) {
        this.dateChangeStatus = dateChangeStatus;
    }

    public String getDescChangeStatus() {
        return descChangeStatus;
    }

    public void setDescChangeStatus(String descChangeStatus) {
        this.descChangeStatus = descChangeStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
