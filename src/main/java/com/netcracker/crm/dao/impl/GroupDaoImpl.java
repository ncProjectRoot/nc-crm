package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
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

    private SimpleJdbcInsert insert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Long create(Group group) {
        if (group.getId() != null) {
            return -1L;
        }
        Long discountId = null;
        if (group.getDiscount() != null) {
            discountId = getDiscountId(group.getDiscount());
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GROUP_NAME, group.getName())
                .addValue(PARAM_GROUP_DISCOUNT_ID, discountId);
        long id = insert.executeAndReturnKey(params).longValue();
        group.setId(id);
        log.info("Group with id: " + id + " is successfully created.");
        return id;
    }

    @Override
    public Long update(Group group) {
        Long groupId = group.getId();
        if (groupId == null) {
            return -1L;
        }
        Long discountId = null;
        if (group.getDiscount() != null) {
            discountId = getDiscountId(group.getDiscount());
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GROUP_ID, groupId)
                .addValue(PARAM_GROUP_NAME, group.getName())
                .addValue(PARAM_GROUP_DISCOUNT_ID, discountId);
        long affectedRows = namedJdbcTemplate.update(SQL_UPDATE_GROUP, params);
        if (affectedRows == 0) {
            log.error("Group has not been updated");
            return -1L;
        } else {
            log.info("Group with id " + groupId + " was successfully updated");
            return affectedRows;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id < 1) {
            return -1L;
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GROUP_ID, id);
        long deletedRows = namedJdbcTemplate.update(SQL_DELETE_GROUP, params);
        if (deletedRows == 0) {
            log.error("Group has not been deleted");
            return -1L;
        } else {
            log.info("Group with id " + id + " was successfully deleted");
            return deletedRows;
        }
    }

    @Override
    public Long delete(Group group) {
        Long groupId = group.getId();
        if (groupId == null) {
            return -1L;
        } else {
            return delete(groupId);
        }
    }

    @Override
    public Group findById(Long id) {
        log.debug("Start finding group by id");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GROUP_ID, id);
        Group group = null;
        List<Group> groups = namedJdbcTemplate.query(SQL_FIND_GROUP_BY_ID, params, new GroupExtractor(discountDao));
        if (groups.size() != 1) {
            //TODO throw SomeException
        } else {
            group = groups.get(0);
        }
        log.debug("End finding group by id");
        return group;
    }

    @Override
    public List<Group> findByName(String name) {
        log.debug("Start finding groups by name");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GROUP_NAME, "%" + name + "%");
        List<Group> list = namedJdbcTemplate.query(SQL_FIND_GROUP_BY_NAME, params, new GroupExtractor(discountDao));
        log.debug("End finding groups by name");
        return list;
    }

    @Override
    public long getCount() {
        return namedJdbcTemplate.getJdbcOperations().queryForObject(SQL_GET_GROUP_COUNT, Long.class);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.insert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_GROUP_TABLE)
                .usingGeneratedKeyColumns(PARAM_GROUP_ID);
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

    static final class GroupExtractor implements ResultSetExtractor<List<Group>> {

        private DiscountDao discountDao;

        GroupExtractor(DiscountDao discountDao) {
            this.discountDao = discountDao;
        }

        @Override
        public List<Group> extractData(ResultSet rs) throws SQLException, DataAccessException {
            log.debug("Start extracting data");
            List<Group> groups = new ArrayList<>();
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getLong(PARAM_GROUP_ID));
                group.setName(rs.getString(PARAM_GROUP_NAME));
                Long discountId = rs.getLong(PARAM_GROUP_DISCOUNT_ID);
                if (discountId > 0) {
                    group.setDiscount(discountDao.findById(discountId));
                }
                groups.add(group);
            }
            log.debug("End extracting data");
            return groups;
        }
    }
}
