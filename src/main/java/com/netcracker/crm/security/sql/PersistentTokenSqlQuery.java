package com.netcracker.crm.security.sql;

/**
 * Created by Pasha on 23.04.2017.
 */
public interface PersistentTokenSqlQuery {
    String TOKEN_BY_SERIES = "SELECT username,series,token,last_used FROM persistent_logins WHERE series = ?";
    String INSERT_TOKEN = "INSERT INTO persistent_logins (username, series, token, last_used) VALUES(?,?,?,?)";
    String UPDATE_TOKEN = "UPDATE persistent_logins SET token = ?, last_used = ? WHERE series = ?";
    String REMOVE_USER_TOKEN = "DELETE FROM persistent_logins WHERE username = ?";
}
