package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.proxy.ComplaintProxy;
import com.netcracker.crm.domain.request.ComplaintRowRequest;
import com.netcracker.crm.domain.request.RowRequest;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.crm.dao.impl.sql.ComplaintSqlQuery.*;

/**
 * Created by Karpunets on 4/29/2017.
 */
@Repository
public class ComplaintDaoImpl implements ComplaintDao {
    private static final Logger log = LoggerFactory.getLogger(ComplaintDaoImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    private SimpleJdbcInsert complaintInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private ComplaintWithDetailExtractor complaintWithDetailExtractor;

    @Override
    public Long create(Complaint complaint) {
        if (complaint.getId() != null) {
            return null;
        }

        Long customerId = getUserId(complaint.getCustomer());
        Long pmgId = getUserId(complaint.getPmg());
        Long orderId = getOrderId(complaint.getOrder());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_TITLE, complaint.getTitle())
                .addValue(PARAM_COMPLAINT_MESSAGE, complaint.getMessage())
                .addValue(PARAM_COMPLAINT_STATUS_ID, complaint.getStatus().getId())
                .addValue(PARAM_COMPLAINT_DATE, complaint.getDate())
                .addValue(PARAM_COMPLAINT_CUSTOMER_ID, customerId)
                .addValue(PARAM_COMPLAINT_PMG_ID, pmgId)
                .addValue(PARAM_COMPLAINT_ORDER_ID, orderId);

        long newId = complaintInsert.executeAndReturnKey(params)
                .longValue();

        complaint.setId(newId);

        log.info("Complaint with id: " + newId + " is successfully created.");
        return newId;
    }

    @Override
    public Long update(Complaint complaint) {
        Long complaintId = complaint.getId();
        if (complaintId == null) {
            return null;
        }
        Long customerId = getUserId(complaint.getCustomer());
        Long pmgId = getUserId(complaint.getPmg());
        Long orderId = getOrderId(complaint.getOrder());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_ID, complaintId)
                .addValue(PARAM_COMPLAINT_TITLE, complaint.getTitle())
                .addValue(PARAM_COMPLAINT_MESSAGE, complaint.getMessage())
                .addValue(PARAM_COMPLAINT_STATUS_ID, complaint.getStatus().getId())
                .addValue(PARAM_COMPLAINT_DATE, complaint.getDate())
                .addValue(PARAM_COMPLAINT_CUSTOMER_ID, customerId)
                .addValue(PARAM_COMPLAINT_PMG_ID, pmgId)
                .addValue(PARAM_COMPLAINT_ORDER_ID, orderId);

        int updatedRows = namedJdbcTemplate.update(SQL_UPDATE_COMPLAINT, params);

        if (updatedRows > 0) {
            log.info("Complaint with id: " + complaintId + " is successfully updated.");
            return complaintId;
        } else {
            log.error("Complaint was not updated.");
            return null;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null) {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue(PARAM_COMPLAINT_ID, id);
            long deletedRows = namedJdbcTemplate.update(SQL_DELETE_COMPLAINT, params);
            if (deletedRows == 0) {
                log.error("Complaint has not been deleted");
                return null;
            } else {
                log.info("Complaint with id " + id + " was successfully deleted");
                return deletedRows;
            }
        }
        return null;
    }

    @Override
    public Long delete(Complaint complaint) {
        if (complaint != null) {
            return delete(complaint.getId());
        }
        return null;
    }

    @Override
    public Complaint findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_ID, id);
        List<Complaint> allComplaint = namedJdbcTemplate.query(SQL_FIND_COMPLAINT_BY_ID, params, complaintWithDetailExtractor);
        Complaint complaint = null;
        if (allComplaint.size() != 0) {
            complaint = allComplaint.get(0);
        }
        return complaint;
    }

    @Override
    public List<Complaint> findByTitle(String title) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_TITLE, title);
        return namedJdbcTemplate.query(SQL_FIND_COMPLAINT_BY_TITLE, params, complaintWithDetailExtractor);
    }

    @Override
    public List<Complaint> findAllByDate(LocalDate date) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_DATE, date);
        return namedJdbcTemplate.query(SQL_FIND_ALL_COMPLAINT_BY_DATE, params, complaintWithDetailExtractor);
    }

    @Override
    public List<Complaint> findAllByCustomerId(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_CUSTOMER_ID, id);
        return namedJdbcTemplate.query(SQL_FIND_ALL_COMPLAINT_BY_CUSTOMER_ID, params, complaintWithDetailExtractor);
    }

    private Long getUserId(User user) {
        if (user != null) {
            Long userId = user.getId();
            if (userId != null) {
                return userId;
            }
            userId = userDao.create(user);

            return userId;
        }
        return null;
    }

    private Long getOrderId(Order order) {
        if (order != null) {
            Long orderId = order.getId();
            if (orderId != null) {
                return orderId;
            }
            orderId = orderDao.create(order);

            return orderId;
        }
        return null;
    }

    @Override
    public List<Complaint> findComplaintRows(ComplaintRowRequest complaintRowRequest) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_ROW_STATUS, complaintRowRequest.getStatusId())
                .addValue(PARAM_COMPLAINT_ROW_ORDER_STATUS, complaintRowRequest.getOrderStatusId())
                .addValue(PARAM_COMPLAINT_ROW_PRODUCT_STATUS, complaintRowRequest.getProductStatusId())
                .addValue(RowRequest.PARAM_ROW_LIMIT, complaintRowRequest.getRowLimit())
                .addValue(RowRequest.PARAM_ROW_OFFSET, complaintRowRequest.getRowOffset());

        String query = complaintRowRequest.getSql();

        if (complaintRowRequest.getPmgId() != null) {
            params.addValue(PARAM_COMPLAINT_PMG_ID, complaintRowRequest.getPmgId());
        }
        if (complaintRowRequest.getCustId() != null) {
            params.addValue(PARAM_COMPLAINT_CUSTOMER_ID, complaintRowRequest.getCustId());
        }

        if (complaintRowRequest.getKeywordsArray() != null) {
            int i = 0;
            for (String keyword : complaintRowRequest.getKeywordsArray()) {
                params.addValue(RowRequest.PARAM_KEYWORD + i++, "%" + keyword + "%");
            }
        }
        return namedJdbcTemplate.query(query, params, complaintWithDetailExtractor);
    }

    @Override
    public Long getComplaintRowsCount(ComplaintRowRequest complaintRowRequest) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PARAM_COMPLAINT_ROW_STATUS, complaintRowRequest.getStatusId());
        params.addValue(PARAM_COMPLAINT_ROW_ORDER_STATUS, complaintRowRequest.getOrderStatusId());
        params.addValue(PARAM_COMPLAINT_ROW_PRODUCT_STATUS, complaintRowRequest.getProductStatusId());

        String query = complaintRowRequest.getSqlCount();

        if (complaintRowRequest.getPmgId() != null) {
            params.addValue(PARAM_COMPLAINT_PMG_ID, complaintRowRequest.getPmgId());
        }
        if (complaintRowRequest.getCustId() != null) {
            params.addValue(PARAM_COMPLAINT_CUSTOMER_ID, complaintRowRequest.getCustId());
        }

        if (complaintRowRequest.getKeywordsArray() != null) {
            int i = 0;
            for (String keyword : complaintRowRequest.getKeywordsArray()) {
                params.addValue(RowRequest.PARAM_KEYWORD + i++, "%" + keyword + "%");
            }
        }
        return namedJdbcTemplate.queryForObject(query, params, Long.class);
    }

    @Override
    public List<String> findComplaintsTitleLikeTitle(String likeTitle) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PARAM_COMPLAINT_TITLE, "%" + likeTitle + "%");
        return namedJdbcTemplate.queryForList(SQL_FIND_COMPLAINTS_TITLES_LIKE_TITLE, params, String.class);
    }

    @Override
    public List<String> findComplaintsTitleByPmgId(String likeTitle, Long pmgId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PARAM_COMPLAINT_TITLE, "%" + likeTitle + "%");
        params.addValue(PARAM_COMPLAINT_PMG_ID, pmgId);
        return namedJdbcTemplate.queryForList(SQL_FIND_COMPLAINTS_TITLES_BY_PMG_ID, params, String.class);
    }

    @Override
    public List<String> findComplaintsTitleByCustId(String likeTitle, Long custId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PARAM_COMPLAINT_TITLE, "%" + likeTitle + "%");
        params.addValue(PARAM_COMPLAINT_CUSTOMER_ID, custId);
        return namedJdbcTemplate.queryForList(SQL_FIND_COMPLAINTS_TITLES_BY_CUSTOMER_ID, params, String.class);
    }

    @Override
    public Long checkOwnershipOfContactPerson(Long complaintId, Long custId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_ID, complaintId)
                .addValue(PARAM_COMPLAINT_CUSTOMER_ID, custId);
        return namedJdbcTemplate.queryForObject(SQL_CHECK_OWNERSHIP_OF_CONTACT_PERSON, params, Long.class);
    }

    @Override
    public Long checkOwnershipOfCustomer(Long complaintId, Long custId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_COMPLAINT_ID, complaintId)
                .addValue(PARAM_COMPLAINT_CUSTOMER_ID, custId);
        return namedJdbcTemplate.queryForObject(SQL_CHECK_OWNERSHIP_OF_CUSTOMER, params, Long.class);
    }

    @Override
    public List<String> findComplaintsTitleForContactPerson(String likeTitle, Long custId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PARAM_COMPLAINT_TITLE, "%" + likeTitle + "%");
        params.addValue(PARAM_COMPLAINT_CUSTOMER_ID, custId);
        return namedJdbcTemplate.queryForList(SQL_FIND_COMPLAINTS_TITLES_FOR_CONTACT_PERSON, params, String.class);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.complaintInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_COMPLAINT_TABLE)
                .usingGeneratedKeyColumns(PARAM_COMPLAINT_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.complaintWithDetailExtractor = new ComplaintWithDetailExtractor(userDao, orderDao);
    }

    private static final class ComplaintWithDetailExtractor implements ResultSetExtractor<List<Complaint>> {
        private UserDao userDao;
        private OrderDao orderDao;

        ComplaintWithDetailExtractor(UserDao userDao, OrderDao orderDao) {
            this.userDao = userDao;
            this.orderDao = orderDao;
        }

        @Override
        public List<Complaint> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<Complaint> allComplaint = new ArrayList<>();
            while (rs.next()) {
                ComplaintProxy complaint = new ComplaintProxy(userDao, orderDao);
                complaint.setId(rs.getLong(PARAM_COMPLAINT_ID));
                complaint.setTitle(rs.getString(PARAM_COMPLAINT_TITLE));
                complaint.setMessage(rs.getString(PARAM_COMPLAINT_MESSAGE));

                Long statusId = rs.getLong(PARAM_COMPLAINT_STATUS_ID);

                Status status = Status.getStatusByID(statusId);
                if (status instanceof ComplaintStatus) {
                    complaint.setStatus((ComplaintStatus) status);
                }

                complaint.setDate(rs.getTimestamp(PARAM_COMPLAINT_DATE).toLocalDateTime());
                complaint.setCustomerId(rs.getLong(PARAM_COMPLAINT_CUSTOMER_ID));
                complaint.setPmgId(rs.getLong(PARAM_COMPLAINT_PMG_ID));
                complaint.setOrderId(rs.getLong(PARAM_COMPLAINT_ORDER_ID));

                allComplaint.add(complaint);
            }
            return allComplaint;
        }
    }
}
