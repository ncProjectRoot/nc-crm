package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.dao.RegionGroupsDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.crm.dao.impl.sql.RegionGroupsSqlQuery.*;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 28.04.2017
 */

@Repository
public class RegionGroupsDaoImpl implements RegionGroupsDao {
    private static final Logger log = LoggerFactory.getLogger(RegionGroupsDao.class);

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private GroupDao groupDao;

    private SimpleJdbcInsert simpleInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Long create(Region region, Group group) {
        Long regionId = getRegionId(region);
        Long groupId = getGroupId(group);
        if (regionId == null || groupId == null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_RG_GROUP_ID, groupId)
                .addValue(PARAM_RG_REGION_ID, regionId);
        Long idRow = simpleInsert.executeAndReturnKey(params).longValue();
        log.info("Group with id " + groupId + " added for region with id " + regionId);
        return idRow;
    }

    @Override
    public Long delete(Region region, Group group) {
        Long regionId = region.getId();
        Long groupId = group.getId();
        if (regionId == null || groupId == null) {
            return null;
        } else {
            return delete(regionId, groupId);
        }
    }

    @Override
    public Long delete(Long regionId, Long groupId) {
        if (regionId < 1 || groupId < 1) {
            return null;
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_RG_REGION_ID, regionId)
                .addValue(PARAM_RG_GROUP_ID, groupId);
        long deletedRows = namedJdbcTemplate.update(SQL_DELETE_RG, params);
        if (deletedRows == 0) {
            log.error("Row has not been deleted");
            return null;
        } else {
            log.info("Group with id " + groupId + " was deleted from Region " + regionId);
            return deletedRows;
        }
    }

    @Override
    public List<Group> findGroupsByRegion(Region region) {
        log.debug("Start finding groups by region");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_RG_REGION_ID, region.getId());
        List<Group> groups = namedJdbcTemplate.query(SQL_FIND_GROUPS_BY_REGION, params, new GroupExtractor());
        log.debug("End finding groups by region");
        return groups;
    }

    @Override
    public List<Region> findRegionsByGroup(Group group) {
        log.debug("Start finding regions by group");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_RG_GROUP_ID, group.getId());
        List<Region> regions = namedJdbcTemplate.query(SQL_FIND_REGIONS_BY_GROUP, params, new RegionExtractor());
        log.debug("End finding regions by group");
        return regions;
    }

    private Long getRegionId(Region region) {
        if (region != null) {
            Long regionId = region.getId();
            if (regionId != null) {
                return regionId;
            }
            regionId = regionDao.create(region);
            return regionId;
        }
        return null;
    }

    private Long getGroupId(Group group) {
        if (group != null) {
            Long groupId = group.getId();
            if (groupId != null) {
                return groupId;
            }
            groupId = groupDao.create(group);
            return groupId;
        }
        return null;
    }

    @Autowired
    public void setDataSours(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_RG_TABLE)
                .usingGeneratedKeyColumns(PARAM_RG_ID);
    }

    private static final class RegionExtractor implements ResultSetExtractor<List<Region>> {
        @Override
        public List<Region> extractData(ResultSet rs) throws SQLException, DataAccessException {
            log.debug("Start extracting data");
            List<Region> regions = new ArrayList<>();
            while (rs.next()) {
                Region region = new Region();
                region.setId(rs.getLong(PARAM_RG_REGION_ID));
                region.setName(rs.getString(PARAM_RG_REGION_NAME));
                Long discountId = rs.getLong(PARAM_RG_REGION_DISC_ID);
                if (discountId > 0) {
                    Discount discount = new Discount();
                    discount.setId(discountId);
                    discount.setPercentage(rs.getDouble(PARAM_RG_DISC_PERC));
                    discount.setDescription(rs.getString(PARAM_RG_DISC_DESC));
                    discount.setTitle(rs.getString(PARAM_RG_DISC_TITLE));
                    discount.setActive(rs.getBoolean(PARAM_RG_DISC_ACTIVE));
                    region.setDiscount(discount);
                }
                regions.add(region);
            }
            log.debug("End extracting data");
            return regions;
        }
    }

    private static final class GroupExtractor implements ResultSetExtractor<List<Group>> {
        @Override
        public List<Group> extractData(ResultSet rs) throws SQLException, DataAccessException {
            log.debug("Start extracting data");
            List<Group> groups = new ArrayList<>();
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getLong(PARAM_RG_GROUP_ID));
                group.setName(rs.getString(PARAM_RG_GROUP_NAME));
                Long discountId = rs.getLong(PARAM_RG_GROUP_DISC_ID);
                if (discountId > 0) {
                    Discount discount = new Discount();
                    discount.setId(discountId);
                    discount.setDescription(rs.getString(PARAM_RG_DISC_DESC));
                    discount.setTitle(rs.getString(PARAM_RG_DISC_TITLE));
                    discount.setPercentage(rs.getDouble(PARAM_RG_DISC_PERC));
                    discount.setActive(rs.getBoolean(PARAM_RG_DISC_ACTIVE));
                    group.setDiscount(discount);
                }
                groups.add(group);
            }
            log.debug("End extracting data");
            return groups;
        }
    }

}
