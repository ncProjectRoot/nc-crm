package com.netcracker.crm.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.Date;

import static com.netcracker.crm.security.sql.PersistentTokenSqlQuery.*;

/**
 * Created by Pasha on 23.04.2017.
 */
public class PersistentTokenRepositoryImpl extends JdbcDaoImpl implements PersistentTokenRepository {
    @Autowired
    public PersistentTokenRepositoryImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public void createNewToken(PersistentRememberMeToken token) {
        this.getJdbcTemplate().update(INSERT_TOKEN, token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

    public void updateToken(String series, String tokenValue, Date lastUsed) {
        this.getJdbcTemplate().update(UPDATE_TOKEN, tokenValue, lastUsed, series);
    }

    public void removeUserTokens(String username) {
        this.getJdbcTemplate().update(REMOVE_USER_TOKEN, username);
    }

    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            return this.getJdbcTemplate().queryForObject(TOKEN_BY_SERIES, (rs, rowNum) ->
                            new PersistentRememberMeToken(rs.getString("username"),
                                    rs.getString("series"), rs.getString("token")
                                    , rs.getTimestamp("last_used")), seriesId);
        } catch (EmptyResultDataAccessException emptyRs) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Querying token for series \'" + seriesId + "\' returned no results.", emptyRs);
            }
        } catch (IncorrectResultSizeDataAccessException incorrRsSize) {
            this.logger.error("Querying token for series \'" + seriesId + "\' returned more than one value. Series should be unique");
        } catch (DataAccessException dataAccess) {
            this.logger.error("Failed to load token for series " + seriesId, dataAccess);
        }
        return null;
    }
}
