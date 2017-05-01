package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.domain.model.Discount;
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

import static com.netcracker.crm.dao.impl.sql.RegionSqlQuery.*;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
@Repository
public class RegionDaoImpl implements RegionDao {
    private static final Logger log = LoggerFactory.getLogger(RegionDaoImpl.class);

    @Autowired
    private DiscountDao discountDao;

    private SimpleJdbcInsert regionInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private RegionExtractor regionExtractor;

    @Override
    public Long create(Region region) {
        if (region.getId() != null) {
            return null;
        }
        Long discountId = getDiscountId(region.getDiscount());
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_REGION_NAME, region.getName())
                .addValue(PARAM_REGION_DISCOUNT_ID, discountId);
        Long id = regionInsert.executeAndReturnKey(params).longValue();
        region.setId(id);
        log.info("Region with id: " + id + " is successfully created.");
        return id;
    }


    @Override
    public Long update(Region region) {
        Long regionId = region.getId();
        if (regionId == null) {
            return null;
        }
        Long discountId = getDiscountId(region.getDiscount());
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_REGION_ID, regionId)
                .addValue(PARAM_REGION_NAME, region.getName())
                .addValue(PARAM_REGION_DISCOUNT_ID, discountId);
        long affectedRows = namedJdbcTemplate.update(SQL_UPDATE_REGION, params);
        if (affectedRows == 0) {
            log.error("Region has not been updated");
            return null;
        } else {
            log.info("Region with id " + regionId + " was successfully updated");
            return regionId;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(PARAM_REGION_ID, id);
            long deletedRows = namedJdbcTemplate.update(SQL_DELETE_REGION, params);
            if (deletedRows == 0) {
                log.error("Region has not been deleted");
                return null;
            } else {
                log.info("Region with id " + id + " was successfully deleted");
                return deletedRows;
            }
        }
        return null;
    }

    @Override
    public Long delete(Region region) {
        if (region != null) {
            return delete(region.getId());
        }
        return null;
    }

    @Override
    public Region findById(Long id) {
        log.debug("Start finding region by id");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_REGION_ID, id);
        List<Region> regions = namedJdbcTemplate.query(SQL_FIND_REGION_BY_ID, params, regionExtractor);
        Region region = null;
        if (regions.size() != 0) {
            region = regions.get(0);
        }
        log.debug("End finding region by id");
        return region;
    }

    @Override
    public List<Region> findByName(String name) {
        log.debug("Start finding regions by name");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_REGION_NAME, "%" + name + "%");
        List<Region> list = namedJdbcTemplate.query(SQL_FIND_REGION_BY_NAME, params, regionExtractor);
        log.debug("End finding regions by name");
        return list;
    }

    @Override
    public Long getCount() {
        return namedJdbcTemplate.getJdbcOperations().queryForObject(SQL_GET_REGION_COUNT, Long.class);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        regionInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns(PARAM_REGION_ID)
                .withTableName(PARAM_REGION_TABLE);
        regionExtractor = new RegionExtractor(discountDao);
    }

    private Long getDiscountId(Discount discount) {
        if (discount != null) {
            Long discountId = discount.getId();
            if (discountId != null) {
                return discountId;
            } else {
                return discountDao.create(discount);
            }
        }
        return null;
    }

    static final class RegionExtractor implements ResultSetExtractor<List<Region>> {

        private DiscountDao discountDao;

        RegionExtractor(DiscountDao discountDao) {
            this.discountDao = discountDao;
        }

        @Override
        public List<Region> extractData(ResultSet rs) throws SQLException, DataAccessException {
            log.debug("Start extracting data");
            List<Region> regions = new ArrayList<>();
            while (rs.next()) {
                Region region = new Region();
                region.setId(rs.getLong(PARAM_REGION_ID));
                region.setName(rs.getString(PARAM_REGION_NAME));
                Long discountId = rs.getLong(PARAM_REGION_ID);
                if (discountId > 0) {
                    region.setDiscount(discountDao.findById(discountId));
                }
                regions.add(region);
            }
            log.debug("End extracting data");
            return regions;
        }
    }
}
