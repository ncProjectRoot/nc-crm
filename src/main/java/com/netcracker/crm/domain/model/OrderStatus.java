package com.netcracker.crm.domain.model;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 24.04.2017
 */
public enum OrderStatus implements Status {
    NEW(4L, "NEW"),
    IN_QUEUE(5L, "IN_QUEUE"),
    PROCESSING(6L, "PROCESSING"),
    ACTIVE(7L, "ACTIVE"),
    DISABLED(8L, "DISABLED"),
    PAUSED(9L, "PAUSED");

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
