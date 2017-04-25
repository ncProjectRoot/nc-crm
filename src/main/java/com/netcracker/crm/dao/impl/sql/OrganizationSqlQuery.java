package com.netcracker.crm.dao.impl.sql;

/**
 * Created by bpogo on 4/24/2017.
 */
public final class OrganizationSqlQuery {
    private OrganizationSqlQuery() {
    }

    public static final String PARAM_ORG_TABLE = "organization";
    public static final String PARAM_ORG_ID = "id";
    public static final String PARAM_ORG_NAME = "name";

    public static final String SQL_FIND_ORGANIZATION_BY_NAME = "SELECT id, name FROM organization WHERE name = :name;";

}
