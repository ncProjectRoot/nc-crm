package com.netcracker.crm.dao.impl.sql;

/**
 *
 * @author YARUS
 */
public final class HistorySqlQuery {
    public static final String PARAM_HISTORY_TABLE = "history";
    public static final String PARAM_HISTORY_ID = "id";
    public static final String PARAM_HISTORY_DATE_CHANGE_STATUS = "date_change_status";
    public static final String PARAM_HISTORY_DESC_CHANGE_STATUS = "desc_change_status";    
    public static final String PARAM_HISTORY_NEW_STATUS_ID = "new_status_id";
    public static final String PARAM_HISTORY_ORDER_ID = "order_id";    
    public static final String PARAM_HISTORY_COMPLAINT_ID = "complaint_id";    
    public static final String PARAM_HISTORY_PRODUCT_ID = "product_id";

    public static final String PARAM_GRAPH_DATE_CHANGE = "date_change";
    public static final String PARAM_GRAPH_ELEMENT_ID = "element_id";
    public static final String PARAM_GRAPH_COUNT = "count";
    public static final String PARAM_GRAPH_FROM_DATE = "from_date";
    public static final String PARAM_GRAPH_TO_DATE = "to_date";

    public static final String SQL_UPDATE_HISTORY = "UPDATE history "
            + "SET new_status_id = :new_status_id, date_change_status = :date_change_status, "
            + "desc_change_status = :desc_change_status, order_id = :order_id, "
            + "complaint_id = :complaint_id, product_id = :product_id WHERE id = :id;";
    
    public static final String SQL_DELETE_HISTORY = "DELETE FROM history WHERE id = :id;";
    
    public static final String SQL_FIND_HISTORY_BY_ID = "SELECT id, new_status_id, "
            + "date_change_status, desc_change_status, order_id, complaint_id, "
            + "product_id FROM history WHERE id = :id;";
    
    public static final String SQL_FIND_ALL_HISTORY_BY_DATE = "SELECT id, new_status_id, "
            + "date_change_status, desc_change_status, order_id, complaint_id, "
            + "product_id FROM history WHERE date_trunc('day', date_change_status) = :date_change_status;";
    
    public static final String SQL_FIND_ALL_HISTORY_BY_ORDER_ID = "SELECT id, "
            + "new_status_id, date_change_status, desc_change_status, order_id, "
            + "complaint_id, product_id FROM history WHERE order_id = :order_id;";
    
    public static final String SQL_FIND_ALL_HISTORY_BY_COMPLAINT_ID = "SELECT id, "
            + "new_status_id, date_change_status, desc_change_status, order_id, "
            + "complaint_id, product_id FROM history WHERE complaint_id = :complaint_id;";
    
    public static final String SQL_FIND_ALL_HISTORY_BY_PRODUCT_ID = "SELECT id, "
            + "new_status_id, date_change_status, desc_change_status, order_id, "
            + "complaint_id, product_id FROM history WHERE product_id = :product_id;";

    public static final String SQL_GRAPH_BETWEEN_DATES = ""
            + "SELECT CAST(date_change_status AS DATE) date_change, :element_id element_id, COUNT(:element_id) count "
            + "FROM history "
            + "WHERE CAST(date_change_status AS DATE) BETWEEN :from_date AND :to_date ";

    public static final String SQL_GRAPH_GROUP_BY_AND_ORDER_BY = " "
            + "GROUP BY element_id, date_change "
            + "ORDER BY date_change;";

}
