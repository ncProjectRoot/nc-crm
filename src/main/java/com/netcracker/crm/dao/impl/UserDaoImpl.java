package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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

    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private AddressDao addressDao;

    private SimpleJdbcInsert userInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public long create(User user) {
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

        KeyHolder keys = new GeneratedKeyHolder();
        int affectedRows = namedJdbcTemplate.update(SQL_CREATE_USER, params, keys);

        Long newId = -1L;
        if (affectedRows > 0) {
            newId = (Long) keys.getKeys().get(PARAM_USER_ID);
            user.setId(newId);

            log.info("User with id: " + newId + " is successfully created.");
            return newId;
        } else {

            log.error("User doesn't created.");
            return newId;
        }
    }

    @Override
    public long updatePassword(User user, String password) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_EMAIL, user.getEmail())
                .addValue(PARAM_USER_PASSWORD, password);
        long count = namedJdbcTemplate.update(SQL_USERS_UPDATE_PASSWORD, params);
        if (count == 1) {
            log.info("Update user password with email : " + user.getEmail() + " is successful");
            return count;
        } else if (count > 1) {
            log.error("Update more 1 rows");
            return count;
        } else {
            log.info("Update 0 rows");
            return count;
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
                .addValue(PARAM_USER_ID, id);

        return namedJdbcTemplate.query(SQL_FIND_USER_BY_ID, params, new UserWithDetailExtractor());
    }

    @Override
    public User findByEmail(String email) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_EMAIL, email);

        return namedJdbcTemplate.query(SQL_FIND_USER_BY_EMAIL, params, new UserWithDetailExtractor());
    }


    private Long getAddressId(Address address) {
        if (address != null) {
            Long addressId = address.getId();
            if (addressId != null) {
                return addressId;
            }
            addressId = addressDao.create(address);

            return addressId;
        }
        return null;
    }

    private Long getOrgId(Organization org) {
        if (org != null) {
            Long orgId = org.getId();
            if (orgId != null) {
                return orgId;
            }
            orgId = organizationDao.create(org);

            return orgId;
        }
        return null;
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

                    user.setAddress(address);
                }
            }
            return user;
        }
    }
}
