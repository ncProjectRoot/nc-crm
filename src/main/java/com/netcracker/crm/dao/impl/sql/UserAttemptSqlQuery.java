package com.netcracker.crm.dao.impl.sql;

/**
 * Created by Pasha on 24.04.2017.
 */
public interface UserAttemptSqlQuery {
    String PARAM_ID = "id";
    String PARAM_EMAIL = "email";
    String PARAM_ATTEMPTS = "attempts";
    String PARAM_LAST_MODIFIED = "last_modified";


    String SQL_USER_ATTEMPTS_GET = "SELECT id, email, attempts, last_modified " +
            "FROM nccrmdb.USER_ATTEMPTS " +
            "WHERE email = :email";
    String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO nccrmdb.USER_ATTEMPTS (email, attempts, last_modified) " +
            "VALUES(:email,:attempts, :last_modified)";

    String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE nccrmdb.USER_ATTEMPTS " +
            "SET attempts = attempts + 1, last_modified = :last_modified " +
            "WHERE email = :email";
    String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE nccrmdb.USER_ATTEMPTS " +
            "SET attempts = 0, last_modified = null " +
            "WHERE email = :email";
}
