package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.*;
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
                Complaint complaint = new Complaint();
                complaint.setId(rs.getLong(PARAM_COMPLAINT_ID));
                complaint.setTitle(rs.getString(PARAM_COMPLAINT_TITLE));
                complaint.setMessage(rs.getString(PARAM_COMPLAINT_MESSAGE));

                Long statusId = rs.getLong(PARAM_COMPLAINT_STATUS_ID);

                Status status = Status.getStatusByID(statusId);
                if (status instanceof ComplaintStatus) {
                    complaint.setStatus((ComplaintStatus) status);
                }

                complaint.setDate(rs.getTimestamp(PARAM_COMPLAINT_DATE).toLocalDateTime());

                Long customerId = rs.getLong(PARAM_COMPLAINT_CUSTOMER_ID);
                if (customerId > 0) {
                    complaint.setCustomer(userDao.findById(customerId));
                }

                Long pmgId = rs.getLong(PARAM_COMPLAINT_PMG_ID);
                if (pmgId > 0) {
                    complaint.setPmg(userDao.findById(pmgId));
                }

                Long orderId = rs.getLong(PARAM_COMPLAINT_ORDER_ID);
                if (orderId > 0) {
                    complaint.setOrder(orderDao.findById(orderId));
                }
                allComplaint.add(complaint);
            }
            return allComplaint;
        }
    }
}
