package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.StatusDao;
import com.netcracker.crm.domain.model.Status;
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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.netcracker.crm.dao.impl.sql.StatusSqlQuery.*;

/**
 * Created by Karpunets on 4/29/2017.
 */
@Repository
public class StatusDaoImpl implements StatusDao {
    private static final Logger log = LoggerFactory.getLogger(StatusDaoImpl.class);

    //    private SimpleJdbcInsert statusInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Long create(Status status) {
        throw new NotImplementedException();
    }

    @Override
    public Long update(Status status) {
        throw new NotImplementedException();
    }

    @Override
    public Long delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Long delete(Status object) {
        throw new NotImplementedException();
    }

    @Override
    public Status findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_STATUS_ID, id);

        return namedJdbcTemplate.query(SQL_FIND_STATUS_BY_ID, params, new StatusWithDetailExtractor());
    }

    @Override
    public Status findByName(String name) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_STATUS_NAME, name);

        return namedJdbcTemplate.query(SQL_FIND_STATUS_BY_NAME, params, new StatusWithDetailExtractor());
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//        this.statusInsert = new SimpleJdbcInsert(dataSource)
//                .withTableName(PARAM_STATUS_TABLE)
//                .usingGeneratedKeyColumns(PARAM_STATUS_ID);
    }

    private static final class StatusWithDetailExtractor implements ResultSetExtractor<Status> {
        @Override
        public Status extractData(ResultSet rs) throws SQLException, DataAccessException {
            Status status = null;
            if (rs.next()) {
                status = Status.getStatusByID(rs.getLong(PARAM_STATUS_ID));
            }
            return status;
        }
    }

}
