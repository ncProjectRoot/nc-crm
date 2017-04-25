package com.netcracker.crm.dao.impl.sql;

/**
 * Created by bpogo on 4/22/2017.
 */
public final class UserSqlQuery {
    private UserSqlQuery() {
    }

    public static final String PARAM_USER_TABLE = "user";
    public static final String PARAM_USER_ID = "id";
    public static final String PARAM_USER_EMAIL = "email";
    public static final String PARAM_USER_PHONE = "phone";
    public static final String PARAM_USER_PASSWORD = "password";
    public static final String PARAM_USER_FIRST_NAME = "first_name";
    public static final String PARAM_USER_LAST_NAME = "last_name";
    public static final String PARAM_USER_MIDDLE_NAME = "middle_name";
    public static final String PARAM_USER_IS_ENABLE = "enable";
    public static final String PARAM_USER_CONTACT_PERSON = "contact_person";
    public static final String PARAM_USER_ACCOUNT_NON_LOCKED = "account_non_locked";

    public static final String PARAM_USER_ROLE_ID = "user_role_id";
    public static final String PARAM_USER_ROLE_NAME = "role_name";

    public static final String PARAM_USER_ORG_ID = "org_id";
    public static final String PARAM_USER_ORG_NAME = "org_name";

    public static final String PARAM_USER_ADDRESS_ID = "address_id";
    public static final String PARAM_USER_ADDRESS_LATITUDE = "addr_latitude";
    public static final String PARAM_USER_ADDRESS_LONGITUDE = "addr_longitude";

    public static final String SQL_FIND_USER_BY_EMAIL = "" +
            "SELECT u.id, email, password, phone, first_name, last_name, middle_name," +
            "enable, account_non_locked, user_role_id, role.name role_name," +
            "org_id, org.name org_name, address_id , addr.latitude addr_latitude, addr.longitude addr_longitude " +
            "FROM \"user\" u " +
            "INNER JOIN user_roles role ON user_role_id = role.id " +
            "LEFT JOIN organization org ON org_id = org.id " +
            "LEFT JOIN address addr ON address_id = addr.id " +
            "WHERE email = :email;";
    public static final String SQL_FIND_USER_BY_ID = "" +
            "SELECT u.id, email, password, phone, first_name, last_name, middle_name, " +
            "enable, account_non_locked, user_role_id, role.name role_name, " +
            "org_id, org.name org_name, address_id , addr.latitude addr_latitude, addr.longitude addr_longitude " +
            "FROM \"user\" u  " +
            "INNER JOIN user_roles role ON user_role_id = role.id " +
            "LEFT JOIN organization org ON org_id = org.id " +
            "LEFT JOIN address addr ON address_id = addr.id " +
            "WHERE u.id = :id;";
    public static final String SQL_CREATE_USER = "INSERT INTO \"user\"" +
            "(password, first_name, middle_name, last_name, phone, email, enable, account_non_locked, contact_person, " +
            "address_id, user_role_id, org_id) " +
            "VALUES (:password, :first_name, :middle_name, :last_name, :phone, :email, :enable, :account_non_locked, " +
            ":contact_person, :address_id, :user_role_id, :org_id);";

    public static final String SQL_USERS_UPDATE_LOCKED = "UPDATE \"user\" " +
            "SET account_non_locked = :account_non_locked " +
            "WHERE email = :email;";
    public static final String SQL_USERS_COUNT = "SELECT count(*) " +
            "FROM \"user\" " +
            "WHERE email = :email;";
}
