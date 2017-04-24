package com.netcracker.crm.domain.model;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 24.04.2017
 */
public enum ProductStatus implements Status {
    PLANNED(1L, "PLANNED"),
    ACTUAL(2L, "ACTUAL"),
    OUTDATED(3L, "OUTDATED");

    private Long id;
    private String name;

    ProductStatus(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
