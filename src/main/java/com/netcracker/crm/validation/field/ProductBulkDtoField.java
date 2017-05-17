package com.netcracker.crm.validation.field;

/**
 * Created by Pasha on 06.05.2017.
 */
public enum ProductBulkDtoField implements DtoField {

    DEFAULT_PRICE("defaultPrice", "Price"),
    DESCRIPTION("description", "Description");

    ProductBulkDtoField(String dtoName, String errorName) {
        this.name = dtoName;
        this.errorName = errorName;
    }

    private final String name;
    private final String errorName;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getErrorName() {
        return this.errorName;
    }
}
