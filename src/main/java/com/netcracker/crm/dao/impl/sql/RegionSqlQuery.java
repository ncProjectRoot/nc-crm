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
    public static final String PARAM_REGION_DISC_ID = "d_id";
    public static final String PARAM_REGION_DISC_TITLE = "d_title";
    public static final String PARAM_REGION_DISC_PERC = "d_perc";
    public static final String PARAM_REGION_DISC_DESC = "d_desc";
    public static final String PARAM_REGION_DISC_ACTIVE = "d_active";

    public static final String SQL_UPDATE_REGION = "UPDATE region " +
            "SET name=:name, discount_id=:discount_id " +
            "WHERE id=:id;";

    public static final String SQL_DELETE_REGION = "DELETE FROM region " +
            "WHERE id=:id;";

    public static final String SQL_FIND_REGION_BY_ID = "SELECT r.id, r.name, d.id d_id, d.title d_title, " +
            "d.percentage d_perc, d.description d_desc, d.active d_active " +
            "FROM region r " +
            "LEFT JOIN discount d " +
            "ON d.id = r.discount_id " +
            "WHERE r.id=:id;";

    public static final String SQL_FIND_REGION_BY_NAME = "SELECT r.id, r.name, d.id d_id, d.title d_title, " +
            "d.percentage d_perc, d.description d_desc, d.active d_active " +
            "FROM region r " +
            "LEFT JOIN discount d " +
            "ON d.id = r.discount_id " +
            "WHERE UPPER(r.name) like UPPER(:name) " +
            "ORDER BY r.id;";

    public static final String SQL_GET_REGION_COUNT = "SELECT count(*) " +
            "FROM region;";


}
