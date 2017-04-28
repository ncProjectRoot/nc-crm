package com.netcracker.crm.dao.impl.sql;

/**
 * Created by Pasha on 24.04.2017.
 */
public final class UserAttemptSqlQuery {
    private UserAttemptSqlQuery() {
    }

    public static final String PARAM_ID = "id";
    public static final String PARAM_EMAIL = "user_id";
    public static final String PARAM_ATTEMPTS = "attempts";
    public static final String PARAM_LAST_MODIFIED = "last_modified";


    public static final String SQL_USER_ATTEMPTS_GET = "" +
            "SELECT ua.id, email, attempts, last_modified " +
            "FROM user_attempts  ua " +
            "INNER JOIN users u " +
            "USING(email)" +
            "WHERE email = :email";
    public static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO user_attempts (user_id, attempts, last_modified) " +
            "VALUES(:user_id,:attempts, :last_modified)";

    public static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE user_attempts " +
            "SET attempts = attempts + 1, last_modified = :last_modified " +
            "WHERE user_id = :user_id";
    public static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE user_attempts " +
            "SET attempts = 0, last_modified = null " +
            "WHERE user_id = :user_id";
}
