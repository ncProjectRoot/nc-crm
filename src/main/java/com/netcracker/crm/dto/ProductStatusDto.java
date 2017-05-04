package com.netcracker.crm.dto;

/**
 * Created by Pasha on 02.05.2017.
 */
public class ProductStatusDto {

    private Long id;
    private String name;


    public ProductStatusDto() {
    }

    public ProductStatusDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
