package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
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

import static com.netcracker.crm.dao.impl.sql.UserSqlQuery.*;

/**
 * Created by bpogo on 4/22/2017.
 */
@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private AddressDao addressDao;

    private SimpleJdbcInsert userInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Long create(User user) {
        if (user.getId() != null) {
            return -1L;
        }

        Long addressId = getAddressId(user.getAddress());
        Long orgId = getOrgId(user.getOrganization());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_EMAIL, user.getEmail())
                .addValue(PARAM_USER_PASSWORD, user.getPassword())
                .addValue(PARAM_USER_FIRST_NAME, user.getFirstName())
                .addValue(PARAM_USER_LAST_NAME, user.getLastName())
                .addValue(PARAM_USER_MIDDLE_NAME, user.getMiddleName())
                .addValue(PARAM_USER_CONTACT_PERSON, user.isContactPerson())
                .addValue(PARAM_USER_PHONE, user.getPhone())
                .addValue(PARAM_USER_IS_ENABLE, user.isEnable())
                .addValue(PARAM_USER_ACCOUNT_NON_LOCKED, user.isAccountNonLocked())
                .addValue(PARAM_USER_ROLE_ID, user.getUserRole().getId())
                .addValue(PARAM_USER_ADDRESS_ID, addressId)
                .addValue(PARAM_USER_ORG_ID, orgId);

        long newId = userInsert.executeAndReturnKey(params)
                .longValue();

        log.info("User with id: " + newId + " is successfully created.");
        return newId;
    }

    @Override
    public Long update(User user) {
        if(user == null)
            return null;
        Long addressId = getAddressId(user.getAddress());
        Long orgId = getOrgId(user.getOrganization());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_EMAIL, user.getEmail())
                .addValue(PARAM_USER_PASSWORD, user.getPassword())
                .addValue(PARAM_USER_FIRST_NAME, user.getFirstName())
                .addValue(PARAM_USER_LAST_NAME, user.getLastName())
                .addValue(PARAM_USER_MIDDLE_NAME, user.getMiddleName())
                .addValue(PARAM_USER_CONTACT_PERSON, user.isContactPerson())
                .addValue(PARAM_USER_PHONE, user.getPhone())
                .addValue(PARAM_USER_IS_ENABLE, user.isEnable())
                .addValue(PARAM_USER_ACCOUNT_NON_LOCKED, user.isAccountNonLocked())
                .addValue(PARAM_USER_ROLE_ID, user.getUserRole().getId())
                .addValue(PARAM_USER_ADDRESS_ID, addressId)
                .addValue(PARAM_USER_ORG_ID, orgId);

        int affectedRows = namedJdbcTemplate.update(SQL_UPDATE_USER, params);

        if (affectedRows > 0) {
            log.info("User with id: " + user.getId() + " is successfully updated.");
            return user.getId();
        } else {
            log.error("User was not updated.");
            return -1L;
        }
    }

    @Override
    public Long delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Long delete(User object) {
        throw new NotImplementedException();
    }

    @Override
    public User findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_ID, id);

        return namedJdbcTemplate.query(SQL_FIND_USER_BY_ID, params, new UserWithDetailExtractor());
    }

    @Override
    public User findByEmail(String email) {
        if(email == null)
            return null;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_EMAIL, email);

        return namedJdbcTemplate.query(SQL_FIND_USER_BY_EMAIL, params, new UserWithDetailExtractor());
    }


    private Long getAddressId(Address address) {
        if(address == null)
            return null;
        Long addressId = address.getId();
        if (addressId != null) {
            return addressId;
        }
        addressId = addressDao.create(address);

        return addressId;
    }

    private Long getOrgId(Organization org) {
        if(org == null)
            return null;
        Long orgId = org.getId();
        if (orgId != null) {
            return orgId;
        }
        orgId = organizationDao.create(org);

        return orgId;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.userInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_USER_TABLE)
                .usingGeneratedKeyColumns(PARAM_USER_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final class UserWithDetailExtractor implements ResultSetExtractor<User> {
        @Override
        public User extractData(ResultSet rs) throws SQLException, DataAccessException {
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getLong(PARAM_USER_ID));
                user.setEmail(rs.getString(PARAM_USER_EMAIL));
                user.setPassword(rs.getString(PARAM_USER_PASSWORD));
                user.setPhone(rs.getString(PARAM_USER_PHONE));
                user.setFirstName(rs.getString(PARAM_USER_FIRST_NAME));
                user.setLastName(rs.getString(PARAM_USER_LAST_NAME));
                user.setMiddleName(rs.getString(PARAM_USER_MIDDLE_NAME));
                user.setEnable(rs.getBoolean(PARAM_USER_IS_ENABLE));
                user.setAccountNonLocked(rs.getBoolean(PARAM_USER_ACCOUNT_NON_LOCKED));
                user.setUserRole(UserRole.valueOf(rs.getString(PARAM_USER_ROLE_NAME)));
                user.setContactPerson(rs.getBoolean(PARAM_USER_CONTACT_PERSON));

                Long orgId = rs.getLong(PARAM_USER_ORG_ID);
                if (orgId > 0) {
                    Organization org = new Organization();
                    org.setId(rs.getLong(PARAM_USER_ORG_ID));
                    org.setName(rs.getString(PARAM_USER_ORG_NAME));

                    user.setOrganization(org);
                }

                Long addressId = rs.getLong(PARAM_USER_ADDRESS_ID);
                if (addressId > 0) {
                    Address address = new Address();
                    address.setId(rs.getLong(PARAM_USER_ADDRESS_ID));
                    address.setLatitude(rs.getDouble(PARAM_USER_ADDRESS_LATITUDE));
                    address.setLongitude(rs.getDouble(PARAM_USER_ADDRESS_LONGITUDE));
                    address.setDetails(rs.getString(PARAM_USER_ADDRESS_DETAILS));

                    long regionId = rs.getLong(PARAM_USER_ADDRESS_REGION_ID);
                    if (regionId > 0) {
                        Region region = new Region();
                        region.setId(regionId);
                        region.setName(PARAM_USER_ADDRESS_REGION_NAME);

                        address.setRegion(region);
                    }
                    user.setAddress(address);
                }
            }
            return user;
        }
    }
}
