package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.domain.model.Organization;
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

import static com.netcracker.crm.dao.impl.sql.OrganizationSqlQuery.*;

/**
 * Created by bpogo on 4/24/2017.
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {
    private static final Logger log = LoggerFactory.getLogger(OrganizationDaoImpl.class);

    private SimpleJdbcInsert orgInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private OrganizationWithDetailExtractor organizationWithDetailExtractor;

    @Override
    public Long create(Organization org) {
        if (org.getId() != null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORG_NAME, org.getName());

        Long newId = orgInsert.executeAndReturnKey(params).longValue();
        org.setId(newId);

        log.info("Organization with id: " + newId + " is successfully created.");
        return newId;
    }

    @Override
    public Long update(Organization org) {
        Long orgId = org.getId();
        if (orgId == null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORG_ID, orgId)
                .addValue(PARAM_ORG_NAME, org.getName());

        int updatedRows = namedJdbcTemplate.update(SQL_UPDATE_ORGANIZATION, params);
        if (updatedRows > 0) {
            log.info("Organization with id: " + orgId + " is successfully updated.");
            return orgId;
        } else {
            log.error("Organization was not updated.");
            return null;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null && id > 0) {
            SqlParameterSource params = new MapSqlParameterSource().addValue(PARAM_ORG_ID, id);

            return (long) namedJdbcTemplate.update(SQL_DELETE_ORGANIZATION, params);
        }
        return null;
    }

    @Override
    public Long delete(Organization org) {
        if (org != null) {
            return delete(org.getId());
        }
        return null;
    }

    @Override
    public Organization findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORG_ID, id);

        return namedJdbcTemplate.query(SQL_FIND_ORGANIZATION_BY_ID, params, organizationWithDetailExtractor);
    }

    @Override
    public Organization findByName(String name) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORG_NAME, name);

        return namedJdbcTemplate.query(SQL_FIND_ORGANIZATION_BY_NAME, params, organizationWithDetailExtractor);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.orgInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_ORG_TABLE)
                .usingGeneratedKeyColumns(PARAM_ORG_ID);
        organizationWithDetailExtractor = new OrganizationWithDetailExtractor();
    }

    private static final class OrganizationWithDetailExtractor implements ResultSetExtractor<Organization> {
        @Override
        public Organization extractData(ResultSet rs) throws SQLException, DataAccessException {
            Organization organization = null;
            if (rs.next()) {
                organization = new Organization();
                organization.setId(rs.getLong(PARAM_ORG_ID));
                organization.setName(rs.getString(PARAM_ORG_NAME));
            }
            return organization;
        }
    }
}
