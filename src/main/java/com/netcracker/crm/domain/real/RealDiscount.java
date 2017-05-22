package com.netcracker.crm.domain.real;

import com.netcracker.crm.domain.model.Discount;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
public class RealDiscount implements Discount {
    private Long id;
    private String title;
    private Double percentage;
    private String description;
    private boolean active;

    public RealDiscount() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Double getPercentage() {
        return percentage;
    }

    @Override
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}