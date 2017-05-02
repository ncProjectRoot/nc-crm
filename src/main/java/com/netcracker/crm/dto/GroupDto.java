package com.netcracker.crm.dto;

import com.netcracker.crm.domain.model.Discount;

/**
 * Created by Pasha on 02.05.2017.
 */
public class GroupDto {
    private Long id;
    private String name;
    private Discount discount;


    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
