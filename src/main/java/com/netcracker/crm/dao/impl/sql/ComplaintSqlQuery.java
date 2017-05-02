package com.netcracker.crm.dao.impl.sql;

/**
 * Created by Karpunets on 4/29/2017.
 */
public final class ComplaintSqlQuery {
    private ComplaintSqlQuery() {
    }

    public static final String PARAM_COMPLAINT_TABLE = "complaint";
    public static final String PARAM_COMPLAINT_ID = "id";
    public static final String PARAM_COMPLAINT_TITLE = "title";
    public static final String PARAM_COMPLAINT_MESSAGE = "message";
    public static final String PARAM_COMPLAINT_STATUS_ID = "status_id";
    public static final String PARAM_COMPLAINT_DATE = "date";
    public static final String PARAM_COMPLAINT_CUSTOMER_ID = "customer_id";
    public static final String PARAM_COMPLAINT_PMG_ID = "pmg_id";
    public static final String PARAM_COMPLAINT_ORDER_ID = "order_id";

    public static final String SQL_FIND_COMPLAINT_BY_ID = "" +
            "SELECT * FROM complaint " +
            "WHERE id = :id;";

    public static final String SQL_FIND_COMPLAINT_BY_TITLE = "" +
            "SELECT * FROM complaint " +
            "WHERE title = :title;";

    public static final String SQL_FIND_ALL_COMPLAINT_BY_DATE = "" +
            "SELECT * FROM complaint " +
            "WHERE date_trunc('day', date) = :date;";

    public static final String SQL_UPDATE_COMPLAINT = "UPDATE complaint " +
            "SET title=:title, message=:message, status_id=:status_id, date=:date, " +
            "customer_id=:customer_id, pmg_id=:pmg_id, order_id=:order_id " +
            "WHERE id=:id;";

    public static final String SQL_DELETE_COMPLAINT = "DELETE FROM complaint WHERE id = :id;";

}
