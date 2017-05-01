package com.netcracker.crm.dto;

/**
 * Created by bpogo on 5/1/2017.
 */
public class OrganizationDto {
    private Long id;
    private String name;

    public OrganizationDto() {
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
