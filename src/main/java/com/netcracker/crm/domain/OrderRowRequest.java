package com.netcracker.crm.domain;

/**
 * @author Karpunets
 * @since 03.05.2017
 */
public class OrderRowRequest {

    private Integer orderBy;
    private Boolean desc;
    private String keywords;
    private Integer statusId;
    private Integer productStatusId;
    private Long rowLimit;
    private Long rowOffset;

    private static final String BEGIN_SQL = ""
            + "SELECT o.id, product_id, p.title, customer_id, csr_id, date_finish, preferred_date, o.status_id "
            + "FROM orders o "
            + "INNER JOIN product p ON o.product_id = p.id ";
    private static final String BEGIN_SQL_COUNT = "SELECT count(*) "
            + "FROM orders o "
            + "INNER JOIN product p ON o.product_id = p.id ";
    private static final String END_SQL = " LIMIT :row_limit OFFSET :row_offset;";

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getDesc() {
        return desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getProductStatusId() {
        return productStatusId;
    }

    public void setProductStatusId(Integer productStatusId) {
        this.productStatusId = productStatusId;
    }

    public Long getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(Long rowLimit) {
        this.rowLimit = rowLimit;
    }

    public Long getRowOffset() {
        return rowOffset;
    }

    public void setRowOffset(Long rowOffset) {
        this.rowOffset = rowOffset;
    }

    public String getSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(BEGIN_SQL);
        appendWhere(sql);
        sql.append("ORDER BY ").append(orderBy);
        if (desc) {
            sql.append(" DESC");
        } else {
            sql.append(" ASC");
        }
        sql.append(END_SQL);
        return sql.toString();
    }

    public String getSqlCount() {
        StringBuilder sql = new StringBuilder();
        sql.append(BEGIN_SQL_COUNT);
        appendWhere(sql);
        return sql.toString();
    }

    private StringBuilder appendWhere(StringBuilder sql){
        boolean appendedWhere = false;
        if (!keywords.isEmpty()) {
            sql.append("WHERE ");
            appendedWhere = true;
            int keywordsLength = keywords.split("\n").length;
            for (int i = 0; i < keywordsLength; i++) {
                sql.append("( cast(o.id as text) LIKE :keyword").append(i);
                sql.append(" OR cast(p.id as text) LIKE :keyword" ).append(i);
                sql.append(" OR p.title LIKE :keyword").append(i);
                sql.append(" OR cast(date_finish as text) LIKE :keyword").append(i);
                sql.append(" OR cast(customer_id as text) LIKE :keyword").append(i);
                sql.append(" OR cast(csr_id as text) LIKE :keyword").append(i);
                sql.append(" OR cast(preferred_date as text) LIKE :keyword").append(i);
                sql.append(")");
                if (i != keywordsLength - 1) {
                    sql.append(" AND ");
                }
            }
        }
        if (!statusId.equals(0)) {
            if (appendedWhere) {
                sql.append("AND ");
            } else {
                sql.append("WHERE ");
                appendedWhere = true;
            }
            sql.append("o.status_id = :status_id ");
        }
        if (!productStatusId.equals(0)) {
            if (appendedWhere) {
                sql.append("AND ");
            } else {
                sql.append("WHERE ");
            }
            sql.append("p.status_id = :product_status_id ");
        }
        return sql;
    }
}
