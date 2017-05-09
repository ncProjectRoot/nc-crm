package com.netcracker.crm.validation.field;

/**
 * Created by Pasha on 07.05.2017.
 */
public enum OrderDtoField implements DtoField {
    CUSTOMER_ID("customerId", "Client"),
    PRODUCT_ID("productId", "Product");

    private final String name;
    private final String errorName;

    OrderDtoField(String name, String errorName) {
        this.name = name;
        this.errorName = errorName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getErrorName() {
        return this.errorName;
    }
}
