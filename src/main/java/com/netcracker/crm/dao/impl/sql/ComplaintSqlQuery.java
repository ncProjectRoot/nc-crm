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

    public static final String PARAM_COMPLAINT_ROW_STATUS = "status_id";
    public static final String PARAM_COMPLAINT_ROW_PRODUCT_STATUS = "product_status_id";
    public static final String PARAM_COMPLAINT_ROW_ORDER_STATUS = "order_status_id";

    public static final String PARAM_COMPLAINT_DATE_FIRST = "date_first";
    public static final String PARAM_COMPLAINT_DATE_LAST = "date_last";
    public static final String PARAM_COMPLAINT_CUSTOMER_ID_LIST = "customer_id_list";

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

    public static final String SQL_FIND_ALL_COMPLAINT_BY_CUSTOMER_ID = "" +
            "SELECT id, title, message, status_id, date, " +
            "customer_id, pmg_id, order_id FROM complaint " +
            "WHERE customer_id = :customer_id " +
            "ORDER BY date desc;";


    public static final String SQL_FIND_COMPLAINTS_TITLES_LIKE_TITLE = "" +
            "SELECT title " +
            "FROM complaint " +
            "WHERE title ILIKE :title " +
            "ORDER BY title " +
            "LIMIT 5;";

    public static final String SQL_FIND_COMPLAINTS_TITLES_BY_PMG_ID = "" +
            "SELECT title " +
            "FROM complaint " +
            "WHERE title ILIKE :title AND pmg_id = :pmg_id " +
            "ORDER BY title " +
            "LIMIT 5;";

    public static final String SQL_FIND_COMPLAINTS_TITLES_BY_CUSTOMER_ID = "" +
            "SELECT title " +
            "FROM complaint " +
            "WHERE title ILIKE :title AND customer_id = :customer_id " +
            "ORDER BY title " +
            "LIMIT 5;";

    public static final String SQL_CHECK_OWNERSHIP_OF_CUSTOMER = "SELECT count(*) " +
            "FROM complaint " +
            "WHERE id = :id AND customer_id = :customer_id";

    public static final String SQL_CHECK_OWNERSHIP_OF_CONTACT_PERSON = "SELECT count(*) " +
            "FROM complaint " +
            "WHERE id = :id AND customer_id IN (SELECT id " +
            "FROM users " +
            "WHERE org_id = (SELECT org_id " +
            "FROM users " +
            "WHERE id = :customer_id));";

    public static final String SQL_FIND_COMPLAINTS_TITLES_FOR_CONTACT_PERSON = "SELECT title " +
            "FROM complaint " +
            "WHERE title ILIKE :title AND customer_id IN (SELECT id " +
            "FROM users " +
            "WHERE org_id = (SELECT org_id " +
            "FROM users " +
            "WHERE id = :customer_id));";


    public static final String SQL_DELETE_COMPLAINT = "DELETE FROM complaint WHERE id = :id;";





    public static final String SQL_FIND_ALL_COMPLAINTS_BY_PMG_ID_AND_CUSTOMER_ID_BETWEEN_DATES =  ""
            +"SELECT id, title, message, status_id, date, "
            +"customer_id, pmg_id, order_id FROM complaint "
            +"WHERE pmg_id = :pmg_id "
            +"AND customer_id = :customer_id AND date_trunc('day',date) BETWEEN "
            +":date_first AND :date_last "
            +"ORDER BY date desc;";

    public static final String SQL_FIND_ALL_COMPLAINTS_BY_PMG_ID_AND_ARRAY_OF_CUSTOMER_ID_BETWEEN_DATES = ""
            +"SELECT id, title, message, status_id, date, "
            +"customer_id, pmg_id, order_id FROM complaint "
            +"WHERE pmg_id = :pmg_id "
            +"AND customer_id IN (:customer_id_list) AND date_trunc('day',date) BETWEEN "
            +":date_first AND :date_last "
            +"ORDER BY date desc;";

    public static final String SQL_FIND_ALL_COMPLAINTS_BY_PMG_ID_BETWEEN_DATES = ""
            +"SELECT id, title, message, status_id, date, "
            +"customer_id, pmg_id, order_id FROM complaint "
            +"WHERE pmg_id = :pmg_id "
            +"AND date_trunc('day',date) BETWEEN :date_first AND :date_last "
            +"ORDER BY date desc;";

}
