package com.netcracker.crm.dao.impl.sql;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public final class GroupSqlQuery {
    public static final String PARAM_GROUP_TABLE = "groups";
    public static final String PARAM_GROUP_ID = "id";
    public static final String PARAM_GROUP_DISCOUNT = "discount_id";
    public static final String PARAM_GROUP_NAME = "name";
    public static final String PARAM_GROUP_DISC_ID = "d_id";
    public static final String PARAM_GROUP_DISC_TITLE = "d_title";
    public static final String PARAM_GROUP_DISC_PERC = "d_perc";
    public static final String PARAM_GROUP_DISC_DESC = "d_desc";
    public static final String PARAM_GROUP_DISC_ACTIVE = "d_active";

    public static final String SQL_UPDATE_GROUP = "UPDATE groups " +
            "SET name=:name, discount_id=:discount_id " +
            "WHERE id=:id;";

    public static final String SQL_DELETE_GROUP = "DELETE from groups " +
            "WHERE id=:id;";

    public static final String SQL_FIND_GROUP_BY_ID = "SELECT g.id, g.name, d.id d_id, d.title d_title, " +
            "d.percentage d_perc, d.description d_desc, d.active d_active " +
            "FROM groups g " +
            "LEFT JOIN discount d " +
            "ON d.id = g.discount_id " +
            "WHERE g.id=:id;";

    public static final String SQL_FIND_GROUP_BY_NAME = "SELECT g.id, g.name, d.id d_id, d.title d_title, " +
            "d.percentage d_perc, d.description d_desc, d.active d_active " +
            "FROM groups g " +
            "LEFT JOIN discount d " +
            "ON d.id = g.discount_id " +
            "WHERE UPPER(g.name) like UPPER(:name) " +
            "ORDER BY g.id;";

    public static final String SQL_GET_GROUP_COUNT = "SELECT count(*) " +
            "FROM groups;";

}


