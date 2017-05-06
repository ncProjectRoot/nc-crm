package com.netcracker.crm.dao.impl.sql;

/**
 * Created by Karpunets on 4/29/2017.
 */
public final class ProductSqlQuery {
    public static final String PARAM_PRODUCT_TABLE = "product";
    public static final String PARAM_PRODUCT_ID = "id";
    public static final String PARAM_PRODUCT_TITLE = "title";
    public static final String PARAM_PRODUCT_DEFAULT_PRICE = "default_price";
    public static final String PARAM_PRODUCT_STATUS_ID = "status_id";
    public static final String PARAM_PRODUCT_DESCRIPTION = "description";
    public static final String PARAM_PRODUCT_DISCOUNT_ID = "discount_id";
    public static final String PARAM_PRODUCT_GROUP_ID = "group_id";

    public static final String PARAM_PRODUCT_ROW_STATUS = "status_id";
    public static final String PARAM_PRODUCT_ROW_DISCOUNT_ACTIVE = "active";
    
    public static final String SQL_UPDATE_PRODUCT = "UPDATE product "
            + "SET title = :title, default_price = :default_price, "
            + "status_id = :status_id, description = :description, "
            + "discount_id = :discount_id, group_id = :group_id WHERE id = :id;";
    
    public static final String SQL_FIND_PRODUCT_BY_ID = ""
            + "SELECT id, title, default_price,"
            + "status_id, description, discount_id, group_id "
            + "FROM product "
            + "WHERE id = :id;";
    
    public static final String SQL_FIND_PRODUCT_BY_TITLE = ""
            + "SELECT id, title, default_price,"
            + "status_id, description, discount_id, group_id "
            + "FROM product "
            + "WHERE title = :title;";

    public static final String SQL_FIND_ALL_PRODUCT_BY_GROUP_ID = ""
            + "SELECT id, title, default_price,"
            + "status_id, description, discount_id, group_id "
            + "FROM product "
            + "WHERE group_id = :group_id;";
    public static final String SQL_FIND_ALL_PRODUCT_WITHOUT_GROUP = ""
            + "SELECT id, title, default_price,"
            + "status_id, description, discount_id, group_id "
            + "FROM product "
            + "WHERE group_id IS null;";

    public static final String SQL_FIND_PRODUCT_TITLES_LIKE_TITLE = ""
            + "SELECT title "
            + "FROM product "
            + "WHERE title ILIKE :title "
//            + "ORDER BY random() DESC "
            + "LIMIT 5;";

    public static final String SQL_DELETE_PRODUCT = "DELETE FROM product WHERE id = :id;";

}
