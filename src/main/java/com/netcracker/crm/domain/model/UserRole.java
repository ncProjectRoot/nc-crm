package com.netcracker.crm.domain.model;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 24.04.2017
 */

public enum UserRole {
    ROLE_ADMIN(1L, "ROLE_ADMIN"),
    ROLE_CUSTOMER(2L, "ROLE_CUSTOMER"),
    ROLE_CSR(3L, "ROLE_CSR"),
    ROLE_PMG(4L, "ROLE_PMG");

    private Long id;
    private String name;

    UserRole(Long id, String name) {
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
