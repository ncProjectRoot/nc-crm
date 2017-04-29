package com.netcracker.crm.dao.impl.sql;

/**
 * Created by Karpunets on 4/29/2017.
 */
public final class StatusSqlQuery {
    private StatusSqlQuery() {
    }

    public static final String PARAM_STATUS_TABLE = "statuses";
    public static final String PARAM_STATUS_ID = "id";
    public static final String PARAM_STATUS_NAME = "name";

    public static final String SQL_FIND_STATUS_BY_ID = "SELECT id, name FROM statuses WHERE id = :id;";
    public static final String SQL_FIND_STATUS_BY_NAME = "SELECT id, name FROM statuses WHERE name = :name;";

    public static final String SQL_UPDATE_STATUS = "UPDATE statuses " +
            "SET name=:name " +
            "WHERE id=:id;";

}
