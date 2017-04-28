package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.domain.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;

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
        throw new NotImplementedException();
    }

    @Override
    public Long update(Address address) {
        throw new NotImplementedException();
    }

    @Override
    public Long delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Long delete(Address object) {
        throw new NotImplementedException();
    }

    @Override
    public Address findById(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Address findByName(String name) {
        throw new NotImplementedException();
    }



    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.addressInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_ADDRESS_TABLE)
                .usingGeneratedKeyColumns(PARAM_ADDRESS_ID);
    }
}
