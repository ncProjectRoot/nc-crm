package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
        return 0;
    }

    @Override
    public long delete(Long id) {
        return 0;
    }

    @Override
    public Discount findById(Long id) {
        return null;
    }

    @Override
    public Discount findByTitle(String title) {
        return null;
    }


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.discountInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_DISCOUNT_TABLE)
                .usingGeneratedKeyColumns(PARAM_DISCOUNT_ID);
    }
}
