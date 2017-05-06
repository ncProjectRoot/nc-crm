package com.netcracker.crm.domain.request;

/**
 * @author Karpunets
 * @since 05.05.2017
 */
public class ProductRowRequest extends RowRequest {

    private Integer statusId;
    private Boolean discountActive;
    private Long customerId;

    private static final String BEGIN_SQL = ""
            + "SELECT p.id, p.title, default_price, p.discount_id,"
            + "d.percentage, group_id, status_id, d.active, p.description "
            + "FROM product p "
            + "LEFT JOIN discount d ON p.discount_id = d.id "
            + "LEFT JOIN groups g ON p.group_id = g.id";
    private static final String BEGIN_SQL_COUNT = "SELECT count(*) "
            + "FROM product p "
            + "LEFT JOIN discount d ON p.discount_id = d.id "
            + "LEFT JOIN groups g ON p.group_id = g.id";

    public ProductRowRequest() {
        super(new String[]{
                "p.id",
                "p.title",
                "default_price",
                "p.discount_id",
                "d.percentage",
                "group_id"
        });
    }


    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Boolean getDiscountActive() {
        return discountActive;
    }

    public void setDiscountActive(Boolean discountActive) {
        this.discountActive = discountActive;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    @Override
    protected String beginSql() {
        return BEGIN_SQL;
    }

    @Override
    protected String beginSqlCount() {
        return BEGIN_SQL_COUNT;
    }

    @Override
    protected StringBuilder appendWhereStatus(StringBuilder sql) {
        if (statusId != null) {
            appendWhere(sql);
            sql.append("status_id = :status_id ");
        }
        if (discountActive != null) {
            appendWhere(sql);
            sql.append("d.active = :active ");
        }
        return sql;
    }


}
