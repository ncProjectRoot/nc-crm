package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static com.netcracker.crm.dao.impl.sql.DiscountSqlQuery.*;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */

@Repository
public class DiscountDaoImpl implements DiscountDao {
    private static final Logger log = LoggerFactory.getLogger(DiscountDaoImpl.class);

    private SimpleJdbcInsert discountInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public long create(Discount discount) {
        if (discount.getId() != null) {
            return -1L;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_DISCOUNT_TITLE, discount.getTitle())
                .addValue(PARAM_DISCOUNT_PERCENTAGE, discount.getPercentage())
                .addValue(PARAM_DISCOUNT_DESCRIPTION, discount.getDescription())
                .addValue(PARAM_DISCOUNT_DATE_START, discount.getDateStart())
                .addValue(PARAM_DISCOUNT_DATE_FINISH, discount.getDateFinish());
        Long id = discountInsert.executeAndReturnKey(params).longValue();
        discount.setId(id);
        log.info("Discount with id: " + id + " is successfully created.");
        return id;
    }

    @Override
    public long update(Discount discount) {
        Long discountId = discount.getId();
        if (discountId == null) {
            return -1L;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_DISCOUNT_ID, discountId)
                .addValue(PARAM_DISCOUNT_TITLE, discount.getTitle())
                .addValue(PARAM_DISCOUNT_PERCENTAGE, discount.getPercentage())
                .addValue(PARAM_DISCOUNT_DESCRIPTION, discount.getDescription())
                .addValue(PARAM_DISCOUNT_DATE_START, discount.getDateStart())
                .addValue(PARAM_DISCOUNT_DATE_FINISH, discount.getDateFinish());
        int affectedRows = namedJdbcTemplate.update(SQL_UPDATE_DISCOUNT, params);
        if (affectedRows == 0) {
            log.error("Discount has not been updated");
            return -1L;
        } else {
            log.info("Discount with id " + discountId + " was successfully updated");
            return affectedRows;
        }
    }

    @Override
    public long delete(Long id) {
        if (id < 1) {
            return -1L;
        }
        int deletedRows = namedJdbcTemplate.getJdbcOperations().update(SQL_DELETE_DISCOUNT, id);
        if (deletedRows == 0) {
            log.error("Discount has not been deleted");
            return -1L;
        } else {
            log.info("Discount with id " + id + " was successfully deleted");
            return deletedRows;
        }
    }

    @Override
    public long delete(Discount discount) {
        Long discountId = discount.getId();
        if (discountId == null) {
            return -1L;
        } else {
            return delete(discountId);
        }
    }

    @Override
    public Discount findById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PARAM_DISCOUNT_ID, id);
        return namedJdbcTemplate.queryForObject(SQL_FIND_DISC_BY_ID, params, new DiscountRowMapper());
    }

    @Override
    public List<Discount> findByTitle(String title) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_DISCOUNT_TITLE, "%" + title + "%");
        return namedJdbcTemplate.query(SQL_FIND_DISC_BY_TITLE, params, new DiscountRowMapper());
    }

    @Override
    public long getCount() {
        return namedJdbcTemplate.getJdbcOperations().queryForObject(SQL_GET_DISC_COUNT, Long.class);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.discountInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_DISCOUNT_TABLE)
                .usingGeneratedKeyColumns(PARAM_DISCOUNT_ID);
    }

    private static final class DiscountRowMapper implements RowMapper<Discount> {
        @Override
        public Discount mapRow(ResultSet rs, int rowNum) throws SQLException {
            log.debug("Start mapping data");
            Discount discount = new Discount();
            discount.setId(rs.getLong(PARAM_DISCOUNT_ID));
            discount.setDescription(rs.getString(PARAM_DISCOUNT_DESCRIPTION));
            discount.setPercentage(rs.getDouble(PARAM_DISCOUNT_PERCENTAGE));
            discount.setTitle(rs.getString(PARAM_DISCOUNT_TITLE));
            Timestamp dateFromDB = rs.getTimestamp(PARAM_DISCOUNT_DATE_START);
            if (dateFromDB != null) {
                discount.setDateStart(dateFromDB.toLocalDateTime().toLocalDate());
            }
            dateFromDB = rs.getTimestamp(PARAM_DISCOUNT_DATE_FINISH);
            if (dateFromDB != null) {
                discount.setDateFinish(dateFromDB.toLocalDateTime().toLocalDate());
            }
            log.debug("End mapping data");
            return discount;
        }
    }
}
