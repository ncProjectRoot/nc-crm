package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.model.User;
import com.netcracker.crm.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.netcracker.crm.dao.impl.sql.UserSqlQuery.*;

/**
 * Created by bpogo on 4/22/2017.
 */
@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public long create(User user) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_EMAIL, user.getEmail())
                .addValue(PARAM_PASSWORD, user.getPassword())
                .addValue(PARAM_FIRST_NAME, user.getFirstName())
                .addValue(PARAM_LAST_NAME, user.getLastName())
                .addValue(PARAM_MIDDLE_NAME, user.getMiddleName())
                .addValue(PARAM_IS_ENABLE, user.isEnable())
                .addValue(PARAM_ROLE_ID, user.getUserRole().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRows = namedJdbcTemplate.update(SQL_CREATE_USER, params, keyHolder);

        if (affectedRows == 1) {
            Long id = (Long) keyHolder.getKeys().get(PARAM_ID);
            log.info("User with id: " + id + " is successfully created.");
            return id;
        } else {
            log.error("User is not created.");
            return -1L;
        }
    }

    @Override
    public long update(User user) {
        return 0;
    }

    @Override
    public long delete(Long id) {
        return 0;
    }

    @Override
    public User findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ID, id);

        return namedJdbcTemplate.query(SQL_FIND_USER_BY_ID, params, new UserWithDetailExtractor());
    }

    @Override
    public User findByEmail(String email) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_EMAIL, email);

        return namedJdbcTemplate.query(SQL_FIND_USER_BY_EMAIL, params, new UserWithDetailExtractor());
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final class UserWithDetailExtractor implements ResultSetExtractor<User> {
        @Override
        public User extractData(ResultSet rs) throws SQLException, DataAccessException {
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getLong(PARAM_ID));
                user.setEmail(rs.getString(PARAM_EMAIL));
                user.setPassword(rs.getString(PARAM_PASSWORD));
                user.setFirstName(rs.getString(PARAM_FIRST_NAME));
                user.setLastName(rs.getString(PARAM_LAST_NAME));
                user.setMiddleName(rs.getString(PARAM_MIDDLE_NAME));
                user.setEnable(rs.getBoolean(PARAM_IS_ENABLE));
                user.setUserRole(UserRole.valueOf(rs.getString(PARAM_ROLE_NAME)));
            }
            return user;
        }
    }
}
