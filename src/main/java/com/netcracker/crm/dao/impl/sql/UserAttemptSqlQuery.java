package com.netcracker.crm.dao.impl.sql;

/**
 * Created by Pasha on 24.04.2017.
 */
public final class UserAttemptSqlQuery {
    private UserAttemptSqlQuery() {

    }

    public static final String PARAM_ID = "id";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_ATTEMPTS = "attempts";
    public static final String PARAM_LAST_MODIFIED = "last_modified";


    public static final String SQL_USER_ATTEMPTS_GET = "SELECT id, email, attempts, last_modified " +
            "FROM user_attempts " +
            "WHERE email = :email";
    public static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO user_attempts (email, attempts, last_modified) " +
            "VALUES(:email,:attempts, :last_modified)";

    public static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE user_attempts " +
            "SET attempts = attempts + 1, last_modified = :last_modified " +
            "WHERE email = :email";
    public static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE user_attempts " +
            "SET attempts = 0, last_modified = null " +
            "WHERE email = :email";
}
