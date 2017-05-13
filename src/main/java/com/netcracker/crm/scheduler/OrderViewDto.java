package com.netcracker.crm.scheduler;

import com.netcracker.crm.domain.model.Order;

/**
 * Created by Pasha on 13.05.2017.
 */
public class OrderViewDto {
    private Long id;
    private String status;
    private String title;


    public OrderViewDto(Order order) {
        this.id = order.getId();
        this.status = order.getStatus().getName();
        this.title = order.getProduct().getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
