package com.netcracker.crm.dao.impl.sql;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public final class GroupSqlQuery {
    public static final String PARAM_GROUP_TABLE = "groups";
    public static final String PARAM_GROUP_ID = "id";
    public static final String PARAM_GROUP_NAME = "name";
    public static final String PARAM_GROUP_DISCOUNT_ID = "discount_id";

    public static final String SQL_UPDATE_GROUP = "UPDATE groups " +
            "SET name=:name, discount_id=:discount_id " +
            "WHERE id=:id;";

    public static final String SQL_DELETE_GROUP = "DELETE from groups " +
            "WHERE id=:id;";

    public static final String SQL_FIND_GROUP_BY_ID = "SELECT id, name, discount_id " +
            "FROM groups " +
            "WHERE id=:id;";

    public static final String SQL_FIND_GROUP_BY_NAME = "SELECT id, name, discount_id " +
            "FROM groups " +
            "WHERE UPPER(name) like UPPER(:name) " +
            "ORDER BY id;";

    public static final String SQL_GET_GROUP_COUNT = "SELECT count(*) " +
            "FROM groups;";

}


