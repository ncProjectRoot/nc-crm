package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.domain.request.RowRequest;
import com.netcracker.crm.domain.request.UserRowRequest;
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
import java.util.LinkedList;
import java.util.List;

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
    private UserWithDetailExtractor userWithDetailExtractor;

    @Override
    public Long create(User user) {
        if (user.getId() != null) {
            return null;
        }

        Long addressId = null;
        Long orgId = null;
        if (user.getAddress() != null && user.getOrganization() != null) {
            addressId = getAddressId(user.getAddress());
            orgId = getOrgId(user.getOrganization());
        }

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
        user.setId(newId);

        log.info("User with id: " + newId + " is successfully created.");
        return newId;
    }

    @Override
    public Long update(User user) {
        Long userId = user.getId();
        if (userId == null) {
            return null;
        }
        Long addressId = getAddressId(user.getAddress());
        Long orgId = getOrgId(user.getOrganization());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_ID, userId)
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
            log.info("User with id: " + userId + " is successfully updated.");
            return userId;
        } else {
            log.error("User was not updated.");
            return null;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null) {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue(PARAM_USER_ID, id);
            long deletedRows = namedJdbcTemplate.update(SQL_DELETE_USER, params);
            if (deletedRows == 0) {
                log.error("User has not been deleted");
                return null;
            } else {
                log.info("User with id " + id + " was successfully deleted");
                return deletedRows;
            }
        }
        return null;
    }

    @Override
    public Long delete(User user) {
        if (user != null) {
            return delete(user.getId());
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_ID, id);

        List<User> users = namedJdbcTemplate.query(SQL_FIND_USER_BY_ID, params, userWithDetailExtractor);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_EMAIL, email);
        List<User> users = namedJdbcTemplate.query(SQL_FIND_USER_BY_EMAIL, params, userWithDetailExtractor);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }


    private Long getAddressId(Address address) {
        if (address == null)
            return null;
        Long addressId = address.getId();
        if (addressId != null) {
            return addressId;
        }
        addressId = addressDao.create(address);

        return addressId;
    }

    private Long getOrgId(Organization org) {
        if (org == null)
            return null;
        Long orgId = org.getId();
        if (orgId != null) {
            return orgId;
        }
        orgId = organizationDao.create(org);

        return orgId;
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
    public Long getUserRowsCount(UserRowRequest rowRequest) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(RowRequest.PARAM_ROW_LIMIT, rowRequest.getRowLimit())
                .addValue(RowRequest.PARAM_ROW_OFFSET, rowRequest.getRowOffset())
                .addValue(PARAM_USER_ROLE_ID, rowRequest.getRoleId())
                .addValue(PARAM_USER_ACCOUNT_NON_LOCKED, rowRequest.getAccountNonLocked())
                .addValue(PARAM_USER_CONTACT_PERSON, rowRequest.getContactPerson());

        String sql = rowRequest.getSqlCount();

        if (rowRequest.getKeywordsArray() != null) {
            int i = 0;
            for (String keyword : rowRequest.getKeywordsArray()) {
                params.addValue(RowRequest.PARAM_KEYWORD + i++, "%" + keyword + "%");
            }
        }

        return namedJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public List<User> findUsers(UserRowRequest rowRequest) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(RowRequest.PARAM_ROW_LIMIT, rowRequest.getRowLimit())
                .addValue(RowRequest.PARAM_ROW_OFFSET, rowRequest.getRowOffset())
                .addValue(PARAM_USER_ROLE_ID, rowRequest.getRoleId())
                .addValue(PARAM_USER_ACCOUNT_NON_LOCKED, rowRequest.getAccountNonLocked())
                .addValue(PARAM_USER_CONTACT_PERSON, rowRequest.getContactPerson());
        String sql = rowRequest.getSql();

        if (rowRequest.getKeywordsArray() != null) {
            int i = 0;
            for (String keyword : rowRequest.getKeywordsArray()) {
                params.addValue(RowRequest.PARAM_KEYWORD + i++, "%" + keyword + "%");
            }
        }
        return namedJdbcTemplate.query(sql, params, userWithDetailExtractor);
    }

    @Override
    public List<String> findUserLastNamesByPattern(String pattern) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_USER_LAST_NAME, "%" + pattern + "%");

        return namedJdbcTemplate.queryForList(SQL_FIND_USER_LAST_NAMES_BY_PATTERN, params, String.class);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.userInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_USER_TABLE)
                .usingGeneratedKeyColumns(PARAM_USER_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userWithDetailExtractor = new UserWithDetailExtractor(organizationDao, addressDao);
    }

    private static final class UserWithDetailExtractor implements ResultSetExtractor<List<User>> {

        private OrganizationDao organizationDao;
        private AddressDao addressDao;

        UserWithDetailExtractor(OrganizationDao organizationDao, AddressDao addressDao) {
            this.organizationDao = organizationDao;
            this.addressDao = addressDao;
        }

        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> users = new LinkedList<>();
            while (rs.next()) {
                User user = new User();
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
                    user.setOrganization(organizationDao.findById(orgId));
                }

                Long addressId = rs.getLong(PARAM_USER_ADDRESS_ID);
                if (addressId > 0) {
                    user.setAddress(addressDao.findById(addressId));
                }

                users.add(user);
            }
            return users;
        }
    }
}
