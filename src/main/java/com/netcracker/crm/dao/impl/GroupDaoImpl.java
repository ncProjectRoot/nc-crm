package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static com.netcracker.crm.dao.impl.sql.GroupSqlQuery.*;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
@Repository
public class GroupDaoImpl implements GroupDao {
    private static final Logger log = LoggerFactory.getLogger(GroupDaoImpl.class);

    @Autowired
    private DiscountDao discountDao;

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public long create(Group group) {
        if (group.getId() != null) {
            return -1L;
        }
        Long discountId = getDiscountId(group.getDiscount());
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GROUP_NAME, group.getName())
                .addValue(PARAM_GROUP_DISCOUNT, discountId);
        KeyHolder keys = new GeneratedKeyHolder();
        int insertedRows = namedJdbcTemplate.update(SQL_CREATE_GROUP, params, keys);
        if (insertedRows < 1) {
            log.error("Group doesn't created.");
            return -1L;
        } else {
            Long id = (Long) keys.getKeys().get(PARAM_GROUP_ID);
            log.info("Group with id: " + id + " is successfully created.");
            return id;
        }

    }

    @Override
    public long update(Group group) {
        Long groupId = group.getId();
        if (groupId == null) {
            return -1L;
        }
        Long discountId = getDiscountId(group.getDiscount());
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GROUP_ID, groupId)
                .addValue(PARAM_GROUP_NAME, group.getName())
                .addValue(PARAM_GROUP_DISCOUNT, discountId);
        int affectedRows = namedJdbcTemplate.update(SQL_UPDATE_GROUP, params);
        if (affectedRows == 0) {
            log.error("Group has not been updated");
            return -1L;
        } else {
            log.info("Group with id " + groupId + " was successfully updated");
            return affectedRows;
        }
    }

    @Override
    public long delete(Long id) {
        if (id < 1) {
            return -1L;
        }
        int deletedRows = namedJdbcTemplate.getJdbcOperations().update(SQL_DELETE_GROUP, id);
        if (deletedRows == 0) {
            log.error("Group has not been deleted");
            return -1L;
        } else {
            log.info("Group with id " + id + " was successfully deleted");
            return deletedRows;
        }
    }

    @Override
    public long delete(Group group) {
        Long groupId = group.getId();
        if (groupId == null) {
            return -1L;
        } else {
            return delete(groupId);
        }
    }

    @Override
    public Group findById(Long id) {
        return null;
    }

    @Override
    public Group findByName(String name) {
        return null;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

    private Long getDiscountId(Discount discount) {
        Long discountId = discount.getId();
        if (discountId != null) {
            return discountId;
        } else {
            return discountDao.create(discount);
        }
    }
}
