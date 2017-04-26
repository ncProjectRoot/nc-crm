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

    public static final String SQL_CREATE_GROUP = "INSERT into \"group\"" +
            "(name, discount_id)" +
            "VALUES" +
            "(:name, :discount_id);";

}


