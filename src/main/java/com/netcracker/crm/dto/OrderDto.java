package com.netcracker.crm.dto;

/**
 * Created by Pasha on 06.05.2017.
 */
public class OrderDto {
    private Long customerId;
    private Long productId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
