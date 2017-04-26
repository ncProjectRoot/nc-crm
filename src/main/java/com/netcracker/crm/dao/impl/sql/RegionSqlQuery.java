package com.netcracker.crm.dao.impl.sql;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public final class RegionSqlQuery {
    public static final String PARAM_REGION_TABLE = "region";
    public static final String PARAM_REGION_ID = "id";
    public static final String PARAM_REGION_DISCOUNT = "discount_id";
    public static final String PARAM_REGION_NAME = "name";

    public static final String SQL_UPDATE_REGION = "UPDATE region " +
            "SET name=:name, discount_id=:discount_id " +
            "WHERE id=:id;";

    public static final String SQL_DELETE_REGION = "DELETE from region " +
            "WHERE id=:id;";
}
