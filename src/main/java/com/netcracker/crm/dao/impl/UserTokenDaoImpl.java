package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.dao.UserTokenDao;
import com.netcracker.crm.domain.UserToken;
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

import static com.netcracker.crm.dao.impl.sql.UserTokenSqlQuery.*;

/**
 * Created by Pasha on 02.05.2017.
 */
@Repository
public class UserTokenDaoImpl implements UserTokenDao {
    private static final Logger log = LoggerFactory.getLogger(UserTokenDaoImpl.class);
    private SimpleJdbcInsert userTokenInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.userTokenInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_TOKEN_TABLE)
                .usingGeneratedKeyColumns(PARAM_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public Long create(UserToken userToken) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_ID, userDao.findByEmail(userToken.getUserMail()).getId())
                .addValue(PARAM_USED, false)
                .addValue(PARAM_TOKEN, userToken.getToken())
                .addValue(PARAM_DATE_SEND, userToken.getSendDate());

        long id = userTokenInsert.executeAndReturnKey(params).longValue();
        log.info("UserToken with id: " + id + " is successfully created.");
        userToken.setId(id);
        return id;
    }

    @Override
    public Long update(UserToken userToken) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_ID, userDao.findByEmail(userToken.getUserMail()).getId());
        int affectedRows = namedJdbcTemplate.update(SQL_USER_REGISTER_TOKEN_UPDATE, params);
        if (affectedRows == 1) {
            return 1L;
        } else if (affectedRows > 1) {
            log.error("UserToken with id: " + userToken.getId() + " is incorrect updated.");
            return -1L;
        } else {
            log.info("UserToken with id: " + userToken.getId() + " not updated.");
            return 0L;
        }

    }

    @Override
    public UserToken finByUserEmail(String email) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_EMAIL, email);
        return namedJdbcTemplate.query(SQL_USER_REGISTER_TOKEN_GET, params, new UserTokenWithDetailExtractor());
    }


    private static final class UserTokenWithDetailExtractor implements ResultSetExtractor<UserToken> {

        @Override
        public UserToken extractData(ResultSet rs) throws SQLException, DataAccessException {
            UserToken userToken = null;
            while (rs.next()) {
                userToken = new UserToken();
                userToken.setId(rs.getLong(PARAM_ID));
                userToken.setUserMail(rs.getString(PARAM_EMAIL));
                userToken.setSendDate(rs.getTimestamp(PARAM_DATE_SEND).toLocalDateTime());
                userToken.setToken(rs.getString(PARAM_TOKEN));
                userToken.setUsed(rs.getBoolean(PARAM_USED));
            }
            return userToken;
        }
    }
}
