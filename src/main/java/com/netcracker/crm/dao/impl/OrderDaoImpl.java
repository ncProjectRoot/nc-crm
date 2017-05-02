package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.Order;

import java.sql.Timestamp;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.Status;
import com.netcracker.crm.domain.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import static com.netcracker.crm.dao.impl.sql.OrderSqlQuery.*;

/**
 * @author Karpunets
 * @since 01.06.2017
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    private static final Logger log = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    
    private SimpleJdbcInsert complaintInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private HistoryWithDetailExtractor historyWithDetailExtractor;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.complaintInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_ORDER_TABLE)
                .usingGeneratedKeyColumns(PARAM_ORDER_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.historyWithDetailExtractor = new HistoryWithDetailExtractor(userDao, productDao);
    }

    @Override
    public Long create(Order order) {
        if (order.getId() != null) {
            return null;
        }
        Long customerId = getUserId(order.getCustomer());
        Long productId = getProductId(order.getProduct());
        Long csrId = getUserId(order.getCsr());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_DATE_FINISH, order.getDate())
                .addValue(PARAM_ORDER_PREFERRED_DATE, order.getPreferedDate())
                .addValue(PARAM_ORDER_STATUS, order.getStatus().getId())
                .addValue(PARAM_CUSTOMER_ID, customerId)
                .addValue(PARAM_PRODUCT_ID, productId)
                .addValue(PARAM_CSR_ID, csrId);

        long newId = complaintInsert.executeAndReturnKey(params)
                .longValue();
        order.setId(newId);

        log.info("Order with id: " + newId + " is successfully created.");
        return newId;
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

    private Long getProductId(Product product) {
        if (product != null) {
            Long productId = product.getId();
            if (productId != null) {
                return productId;
            }
            productId = productDao.create(product);

            return productId;
        }
        return null;
    }

    @Override
    public Long update(Order order) {
        Long orderId = order.getId();
        if (orderId == null) {
            return null;
        }
        Long customerId = getUserId(order.getCustomer());
        Long productId = getProductId(order.getProduct());
        Long csrId = getUserId(order.getCsr());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_ID, orderId)
                .addValue(PARAM_ORDER_DATE_FINISH, order.getDate())
                .addValue(PARAM_ORDER_PREFERRED_DATE, order.getPreferedDate())
                .addValue(PARAM_ORDER_STATUS, order.getStatus().getId())
                .addValue(PARAM_CUSTOMER_ID, customerId)
                .addValue(PARAM_PRODUCT_ID, productId)
                .addValue(PARAM_CSR_ID, csrId);

        int updatedRows = namedJdbcTemplate.update(SQL_UPDATE_ORDER, params);

        if (updatedRows > 0) {
            log.info("Order with id: " + orderId + " is successfully updated.");
            return orderId;
        } else {
            log.error("Order was not updated.");
            return null;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null) {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue(PARAM_ORDER_ID, id);
            long deletedRows = namedJdbcTemplate.update(SQL_DELETE_ORDER, params);
            if (deletedRows == 0) {
                log.error("Order has not been deleted");
                return null;
            } else {
                log.info("Order with id " + id + " was successfully deleted");
                return deletedRows;
            }
        }
        return null;
    }

    @Override
    public Long delete(Order order) {
        if (order != null) {
            return delete(order.getId());
        }
        return null;
    }

    @Override
    public Order findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_ID, id);
        List<Order> allOrder = namedJdbcTemplate.query(SQL_FIND_ORDER_BY_ID, params, historyWithDetailExtractor);
        Order order = null;
        if (allOrder.size() != 0) {
            order = allOrder.get(0);
        }
        return order;
    }
    
    @Override
    public List<Order> findAllByDateFinish(LocalDate date) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_DATE_FINISH, date);
        return namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_DATE_FINISH, params, historyWithDetailExtractor);
    }

    @Override
    public List<Order> findAllByPreferredDate(LocalDate date) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_PREFERRED_DATE, date);
        return namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_PREFERRED_DATE, params, historyWithDetailExtractor);
    }

    @Override
    public List<Order> findAllByProductId(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_ID, id);
        return namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_PRODUCT_ID, params, historyWithDetailExtractor);
    }

    @Override
    public List<Order> findAllByCustomerId(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_CUSTOMER_ID, id);
        return namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_CUSTOMER_ID, params, historyWithDetailExtractor);
    }

    @Override
    public List<Order> findAllByCsrId(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_CSR_ID, id);
        return namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_CSR_ID, params, historyWithDetailExtractor);
    }

    private static final class HistoryWithDetailExtractor implements ResultSetExtractor<List<Order>> {

        private UserDao userDao;
        private ProductDao productDao;

        HistoryWithDetailExtractor(UserDao userDao, ProductDao productDao) {
            this.userDao = userDao;
            this.productDao = productDao;
        }

        @Override
        public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<Order> allOrder = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong(PARAM_ORDER_ID));

                Timestamp dateFinish = rs.getTimestamp(PARAM_ORDER_DATE_FINISH);
                if (dateFinish != null) {
                    order.setDate(dateFinish.toLocalDateTime());
                }

                Timestamp datePreferred = rs.getTimestamp(PARAM_ORDER_PREFERRED_DATE);
                if (datePreferred != null) {
                    order.setPreferedDate(datePreferred.toLocalDateTime());

                }

                long statusId = rs.getLong(PARAM_ORDER_STATUS);
                Status status = Status.getStatusByID(statusId);
                if (status instanceof OrderStatus) {
                    order.setStatus((OrderStatus) status);
                }
                           
                Long customerId = rs.getLong(PARAM_CUSTOMER_ID);
                if (customerId > 0) {
                    order.setCustomer(userDao.findById(customerId));
                }

                Long productId = rs.getLong(PARAM_PRODUCT_ID);
                if (productId > 0) {
                    order.setProduct(productDao.findById(productId));
                }
                
                Long csrId = rs.getLong(PARAM_CSR_ID);
                if (csrId > 0) {
                    order.setCsr(userDao.findById(csrId));
                }
                
                allOrder.add(order);
            }
            return allOrder;
        }
    }
}
