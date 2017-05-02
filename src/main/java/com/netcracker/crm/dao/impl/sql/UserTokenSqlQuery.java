package com.netcracker.crm.dao.impl.sql;

/**
 * Created by Pasha on 02.05.2017.
 */
public class UserTokenSqlQuery {

    private UserTokenSqlQuery() {

    }

    public static final String PARAM_TOKEN_TABLE= "user_register_token";
    public static final String PARAM_ID = "id";
    public static final String PARAM_USER_ID = "user_id";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_DATE_SEND = "date_send";
    public static final String PARAM_USED = "used";


    public static final String SQL_USER_REGISTER_TOKEN_GET = "SELECT urt.id, u.email, token, date_send, used " +
            "FROM user_register_token urt" +
            "INNER JOIN users u " +
            "ON urt.user_id = u.id" +
            "WHERE u.email = :email";
    public static final String SQL_USER_REGISTER_TOKEN_UPDATE = "UPDATE user_register_token " +
            "SET used = true " +
            "WHERE user_id = :user_id";
}
