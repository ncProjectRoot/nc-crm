/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao.impl.sql;

/**
 *
 * @author YARUS
 */
public final class OrderSqlQuery {

    public static final String PARAM_ORDER_TABLE = "order";
    public static final String PARAM_ORDER_ID = "id";
    public static final String PARAM_ORDER_DATE = "date";
    public static final String PARAM_ORDER_STATUS = "status_id";
    public static final String PARAM_CUSTOMER_ID = "customer_id";
    public static final String PARAM_PRODUCT_ID = "product_id";
    public static final String PARAM_CSR_ID = "csr_id";

    public static final String SQL_CREATE_ORDER = "INSERT INTO order(date, "
            + "actual_status_id, user_id, product_id, csr_id) "
            + "VALUES (:date, :actual_status_id, :user_id, :product_id, :csr_id);";

    public static final String SQL_UPDATE_ORDER = "UPDATE order SET date = :date, "
            + "actual_status_id = :actual_status_id, user_id = :user_id, "
            + "product_id = :product_id, csr_id = :csr_id WHERE id = :id;";
    
    public static final String SQL_DELETE_ORDER = "DELETE FROM order WHERE id = :id;";

    public static final String SQL_FIND_ORDER_BY_ID = "SELECT * FROM order WHERE id = :id;";
    
    public static final String SQL_FIND_ALL_ORDER_BY_DATE = "SELECT * FROM order WHERE date = :date;";
    
    public static final String SQL_FIND_ALL_ORDER_BY_PRODUCT_ID = "SELECT * FROM order WHERE product_id = :product_id;";
    
    public static final String SQL_FIND_ALL_ORDER_BY_CUSTOMER_ID = "";
    
    public static final String SQL_FIND_ALL_ORDER_BY_CSR_ID = "";
}
