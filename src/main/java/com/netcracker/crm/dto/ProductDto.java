package com.netcracker.crm.dto;

import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;

/**
 * Created by Pasha on 29.04.2017.
 */
public class ProductDto {
    private Long id;
    private String title;
    private Double defaultPrice;
    private String statusName;
    private String description;
    private Discount discount;
    private Group group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", defaultPrice=" + defaultPrice +
                ", statusName='" + statusName + '\'' +
                ", description='" + description + '\'' +
                ", discount=" + discount +
                ", group=" + group +
                '}';
    }
}
