package com.netcracker.crm.domain.model;


/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 24.04.2017
 */
public class Product {
    private Long id;
    private String title;
    private Double defaultPrice;
    private ProductStatus status;
    private String description;
    private Discount discount;
    private Group group;

    public Product() {
    }

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

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (defaultPrice != null ? defaultPrice.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }
}
