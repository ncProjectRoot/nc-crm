package com.netcracker.crm.dao.impl.sql;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public final class DiscountSqlQuery {
    public static final String PARAM_DISCOUNT_TABLE = "discount";
    public static final String PARAM_DISCOUNT_ID = "id";
    public static final String PARAM_DISCOUNT_TITLE = "title";
    public static final String PARAM_DISCOUNT_PERCENTAGE = "percentage";
    public static final String PARAM_DISCOUNT_DESCRIPTION = "description";
    public static final String PARAM_DISCOUNT_DATE_START = "date_start";
    public static final String PARAM_DISCOUNT_DATE_FINISH = "date_finish";

    public static final String SQL_UPDATE_DISCOUNT = "UPDATE discount " +
            "SET title=:title, percentage=:percentage, description=:description, " +
            "date_start=:date_start, date_finish=:date_finish " +
            "WHERE id=:id;";


    public static final String SQL_DELETE_DISCOUNT = "DELETE from discount " +
            "WHERE id=?;";
}
