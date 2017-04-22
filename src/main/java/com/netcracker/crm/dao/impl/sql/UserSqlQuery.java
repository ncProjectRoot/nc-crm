package com.netcracker.crm.dao.impl.sql;

/**
 * Created by bpogo on 4/22/2017.
 */
public interface UserSqlQuery {
    String PARAM_ID = "id";
    String PARAM_EMAIL = "email";
    String PARAM_PASSWORD = "password";
    String PARAM_FIRST_NAME = "first_name";
    String PARAM_LAST_NAME = "last_name";
    String PARAM_MIDDLE_NAME = "middle_name";
    String PARAM_IS_ENABLE = "enable";
    String PARAM_ROLE_ID = "role_id";
    String PARAM_ROLE_NAME = "role_name";

    String SQL_FIND_USER_BY_EMAIL = "SELECT u.id, email, password, first_name, last_name, " +
            "middle_name, enable, role_id, role.name role_name " +
            "FROM nccrmdb.\"user\" u  " +
            "INNER JOIN nccrmdb.user_role role ON u.role_id = role.id " +
            "WHERE email = :email;";
    String SQL_FIND_USER_BY_ID = "SELECT u.id, email, password, first_name, last_name, " +
            "middle_name, enable, role_id, role.name role_name " +
            "FROM nccrmdb.\"user\" u  " +
            "INNER JOIN nccrmdb.user_role role ON u.role_id = role.id " +
            "WHERE u.id = :id;";
    String SQL_CREATE_USER = "INSERT INTO nccrmdb.\"user\"" +
            "(email, password, first_name, last_name, middle_name, enable, role_id) " +
            "VALUES (:email, :password, :first_name, :last_name, :middle_name, :enable, :role_id);";
}
