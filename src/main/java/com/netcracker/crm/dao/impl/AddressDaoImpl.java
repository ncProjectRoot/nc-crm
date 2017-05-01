package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Region;
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

    @Autowired
    private RegionDao regionDao;

    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private SimpleJdbcInsert addressInsert;
    private AddressWithDetailExtractor addressWithDetailExtractor;

    @Override
    public Long create(Address address) {
        if (address.getId() != null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ADDRESS_LATITUDE, address.getLatitude())
                .addValue(PARAM_ADDRESS_LONGITUDE, address.getLongitude())
                .addValue(PARAM_ADDRESS_DETAILS, address.getDetails())
                .addValue(PARAM_ADDRESS_REGION_ID, getRegionId(address.getRegion()));

        Long newId = addressInsert.executeAndReturnKey(params).longValue();
        address.setId(newId);

        log.info("Address with id: " + newId + " is successfully created.");
        return newId;
    }

    private Long getRegionId(Region region) {
        Long regionId = region.getId();
        if (regionId != null) {
            return regionId;
        }
        regionId = regionDao.create(region);

        return regionId;
    }

    @Override
    public Long update(Address address) {
        Long addressId = address.getId();
        if (addressId == null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ADDRESS_ID, addressId)
                .addValue(PARAM_ADDRESS_LATITUDE, address.getLatitude())
                .addValue(PARAM_ADDRESS_LONGITUDE, address.getLongitude())
                .addValue(PARAM_ADDRESS_DETAILS, address.getDetails())
                .addValue(PARAM_ADDRESS_REGION_ID, getRegionId(address.getRegion()));

        int updatedRows = namedJdbcTemplate.update(SQL_UPDATE_ADDRESS, params);
        if (updatedRows > 0) {
            log.info("Address with id: " + addressId + " is successfully updated.");
            return addressId;
        } else {
            log.error("Address was not updated.");
            return null;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null && id > 0) {
            SqlParameterSource params = new MapSqlParameterSource().addValue(PARAM_ADDRESS_ID, id);

            return (long) namedJdbcTemplate.update(SQL_DELETE_ADDRESS, params);
        }
        return null;
    }

    @Override
    public Long delete(Address address) {
        if (address != null) {
            return delete(address.getId());
        }
        return null;
    }

    @Override
    public Address findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ADDRESS_ID, id);

        return namedJdbcTemplate.query(SQL_FIND_ADDRESS_BY_ID, params, addressWithDetailExtractor);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.addressInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_ADDRESS_TABLE)
                .usingGeneratedKeyColumns(PARAM_ADDRESS_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.addressWithDetailExtractor = new AddressWithDetailExtractor(regionDao);
    }

    private static final class AddressWithDetailExtractor implements ResultSetExtractor<Address> {

        private RegionDao regionDao;

        AddressWithDetailExtractor(RegionDao regionDao) {
            this.regionDao = regionDao;
        }

        @Override
        public Address extractData(ResultSet rs) throws SQLException, DataAccessException {
            Address address = null;
            if (rs.next()) {
                address = new Address();
                address.setId(rs.getLong(PARAM_ADDRESS_ID));
                address.setLatitude(rs.getDouble(PARAM_ADDRESS_LATITUDE));
                address.setLongitude(rs.getDouble(PARAM_ADDRESS_LONGITUDE));
                address.setDetails(rs.getString(PARAM_ADDRESS_DETAILS));

                long regionId = rs.getLong(PARAM_ADDRESS_REGION_ID);
                address.setRegion(regionDao.findById(regionId));
            }
            return address;
        }
    }
}
