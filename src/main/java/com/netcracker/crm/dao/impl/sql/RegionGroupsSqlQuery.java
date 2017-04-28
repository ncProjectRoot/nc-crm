package com.netcracker.crm.dao.impl.sql;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 28.04.2017
 */

public final class RegionGroupsSqlQuery {
    public static final String PARAM_RG_TABLE = "region_groups";
    public static final String PARAM_RG_ID = "id";
    public static final String PARAM_RG_GROUP_ID = "group_id";
    public static final String PARAM_RG_REGION_ID = "region_id";

    public static final String PARAM_RG_GROUP_NAME = "g_name";
    public static final String PARAM_RG_GROUP_DISC_ID = "g_disc_id";

    public static final String PARAM_RG_REGION_NAME = "r_name";
    public static final String PARAM_RG_REGION_DISC_ID = "r_disc_id";

    public static final String PARAM_RG_DISC_TITLE = "d_title";
    public static final String PARAM_RG_DISC_PERC = "d_perc";
    public static final String PARAM_RG_DISC_DESC = "d_desc";
    public static final String PARAM_RG_DISC_ACTIVE = "d_active";


    public static final String SQL_DELETE_RG = "DELETE FROM region_groups " +
            "WHERE region_id=:region_id AND group_id=:group_id";

    public static final String SQL_FIND_REGIONS_BY_GROUP = "SELECT rg.id, region_id, r.name r_name, r.discount_id r_disc_id, " +
            "d.title d_title, d.percentage d_perc, d.description d_desc, d.active d_active " +
            "FROM region_groups rg " +
            "INNER JOIN region r " +
            "ON rg.region_id = r.id " +
            "LEFT JOIN discount d " +
            "ON r.discount_id = d.id " +
            "WHERE group_id=:group_id;";

    public static final String SQL_FIND_GROUPS_BY_REGION = "SELECT rg.id, group_id, g.name g_name, g.discount_id g_disc_id, " +
            "d.title d_title, d.percentage d_perc, d.description d_desc, d.active d_active " +
            "FROM region_groups rg " +
            "INNER JOIN groups g " +
            "ON rg.group_id = g.id " +
            "LEFT JOIN discount d " +
            "ON g.discount_id = d.id " +
            "WHERE region_id=:region_id;";
}
