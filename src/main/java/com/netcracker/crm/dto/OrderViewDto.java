package com.netcracker.crm.dto;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Pasha on 13.05.2017.
 */
public class OrderViewDto {
    private Long id;
    private String status;
    private String title;
    private String date;
    private boolean timeOver;


    public OrderViewDto(Order order,DateTimeFormatter formatter) {
        this.id = order.getId();
        this.status = order.getStatus().getName();
        this.title = order.getProduct().getTitle();
        this.date = formatter.format(order.getPreferedDate());
        if (order.getStatus() == OrderStatus.PROCESSING) {
            this.timeOver = LocalDateTime.now().isAfter(order.getPreferedDate());
        }
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isTimeOver() {
        return timeOver;
    }

    public void setTimeOver(boolean timeOver) {
        this.timeOver = timeOver;
    }
}
