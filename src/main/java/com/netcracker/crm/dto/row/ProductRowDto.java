package com.netcracker.crm.dto.row;

/**
 * @author Karpunets
 * @since 05.05.2017
 */
public class ProductRowDto {

    private Long id;
    private String title;
    private Double price;
    private String status;
    private Long discount;
    private Double percentage;
    private Boolean discountActive;
    private Long group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Boolean getDiscountActive() {
        return discountActive;
    }

    public void setDiscountActive(Boolean discountActive) {
        this.discountActive = discountActive;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }
}
