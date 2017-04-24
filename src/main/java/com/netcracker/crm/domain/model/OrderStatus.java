package com.netcracker.crm.domain.model;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 24.04.2017
 */
public enum OrderStatus implements Status {
    NEW(1L, "NEW"),
    IN_QUEUE(2L, "IN_QUEUE"),
    PROCESSING(3L, "PROCESSING"),
    ACTIVE(4L, "ACTIVE"),
    DISABLED(5L, "DISABLED"),
    PAUSED(6L, "PAUSED");

    private Long id;
    private String name;

    OrderStatus(Long id, String name) {
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
