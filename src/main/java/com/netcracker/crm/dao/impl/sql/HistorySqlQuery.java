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
    public static final String PARAM_HISTORY_OLD_STATUS_ID = "old_status_id";
    public static final String PARAM_HISTORY_ORDER_ID = "order_id";    
    public static final String PARAM_HISTORY_COMPLAINT_ID = "complaint_id";    
    public static final String PARAM_HISTORY_PRODUCT_ID = "product_id";
    
    public static final String SQL_UPDATE_HISTORY = "UPDATE history "
            + "SET old_status_id = :old_status_id, date_change_status = :date_change_status, "
            + "desc_change_status = :desc_change_status, order_id = :order_id, "
            + "complaint_id = :complaint_id, product_id = :product_id WHERE id = :id;";
    
    public static final String SQL_DELETE_HISTORY = "DELETE FROM history WHERE id = :id;";
    
    public static final String SQL_FIND_HISTORY_BY_ID = "SELECT id, old_status_id, "
            + "date_change_status, desc_change_status, order_id, complaint_id, "
            + "product_id FROM history WHERE id = :id;";
    
    public static final String SQL_FIND_ALL_HISTORY_BY_DATE = "SELECT id, old_status_id, "
            + "date_change_status, desc_change_status, order_id, complaint_id, "
            + "product_id FROM history WHERE date_trunc('day', date_change_status) = :date_change_status;";
    
    public static final String SQL_FIND_ALL_HISTORY_BY_ORDER_ID = "SELECT id, "
            + "old_status_id, date_change_status, desc_change_status, order_id, "
            + "complaint_id, product_id FROM history WHERE order_id = :order_id;";
    
    public static final String SQL_FIND_ALL_HISTORY_BY_COMPLAINT_ID = "SELECT id, "
            + "old_status_id, date_change_status, desc_change_status, order_id, "
            + "complaint_id, product_id FROM history WHERE complaint_id = :complaint_id;";
    
    public static final String SQL_FIND_ALL_HISTORY_BY_PRODUCT_ID = "SELECT id, "
            + "old_status_id, date_change_status, desc_change_status, order_id, "
            + "complaint_id, product_id FROM history WHERE product_id = :product_id;";
}
