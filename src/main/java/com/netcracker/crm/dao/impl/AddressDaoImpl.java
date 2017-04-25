package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.domain.model.Address;
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

import static com.netcracker.crm.dao.impl.sql.AddressSqlQuery.*;

/**
 * Created by bpogo on 4/24/2017.
 */
@Repository
public class AddressDaoImpl implements AddressDao {
    private static final Logger log = LoggerFactory.getLogger(AddressDaoImpl.class);


    private SimpleJdbcInsert addressInsert;

    @Override
    public Long create(Address address) {
        if (address.getId() != null) {
            return -1L;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ADDRESS_LATITUDE, address.getLatitude())
                .addValue(PARAM_ADDRESS_LONGITUDE, address.getLongitude())
                .addValue(PARAM_ADDRESS_REGION_ID, getRegionId(address));

        Long newId = addressInsert.executeAndReturnKey(params).longValue();
        address.setId(newId);

        log.info("Address with id: " + newId + " is successfully created.");
        return newId;
    }

    private Long getRegionId(Address address) {
        //TODO: implement auto defining region for each address
        return 1L;
    }

    @Override
    public Long update(Address address) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

    @Override
    public Address findByName(String name) {
        return null;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.addressInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_ADDRESS_TABLE)
                .usingGeneratedKeyColumns(PARAM_ADDRESS_ID);
    }
}
