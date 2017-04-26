package com.netcracker.crm.dao.impl.sql;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public final class GroupSqlQuery {
    public static final String PARAM_GROUP_TABLE = "group";
    public static final String PARAM_GROUP_ID = "id";
    public static final String PARAM_GROUP_DISCOUNT = "discount_id";
    public static final String PARAM_GROUP_NAME = "name";
    public static final String PARAM_GROUP_DISC_ID = "d_id";
    public static final String PARAM_GROUP_DISC_TITLE = "d_title";
    public static final String PARAM_GROUP_DISC_PERC = "d_perc";
    public static final String PARAM_GROUP_DISC_DESC = "d_desc";
    public static final String PARAM_GROUP_DISC_START = "d_start";
    public static final String PARAM_GROUP_DISC_FINISH = "d_finish";

    public static final String SQL_CREATE_GROUP = "INSERT into \"group\"" +
            "(name, discount_id)" +
            "VALUES" +
            "(:name, :discount_id);";

    public static final String SQL_UPDATE_GROUP = "UPDATE \"group\" " +
            "SET name=:name, discount_id=:discount_id " +
            "WHERE id=:id;";

    public static final String SQL_DELETE_GROUP = "DELETE from \"group\" " +
            "WHERE id=?;";

    public static final String SQL_FIND_GROUP_BY_ID = "SELECT g.id, g.name, d.id d_id, d.title d_title, " +
            "d.percentage d_perc, d.description d_desc, d.date_start d_start, d.date_finish d_finish " +
            "FROM \"group\" g " +
            "LEFT JOIN discount d " +
            "ON d.id = g.discount_id " +
            "WHERE g.id=:id;";

    public static final String SQL_FIND_GROUP_BY_NAME = "SELECT g.id, g.name, d.id d_id, d.title d_title, " +
            "d.percentage d_perc, d.description d_desc, d.date_start d_start, d.date_finish d_finish " +
            "FROM \"group\" g " +
            "LEFT JOIN discount d " +
            "ON d.id = g.discount_id " +
            "WHERE lower(g.name)=lower(:name);";

}


