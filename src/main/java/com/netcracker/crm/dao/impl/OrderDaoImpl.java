
package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.StatusDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.dao.impl.sql.OrderSqlQuery;
import com.netcracker.crm.domain.model.Order;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import static com.netcracker.crm.dao.impl.sql.OrderSqlQuery.*;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author YARUS
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    private static final Logger log = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;
    
    private SimpleJdbcInsert simpleInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns(PARAM_ORDER_ID)
                .withTableName(PARAM_ORDER_TABLE);
    }

    @Override
    public Long create(Order order) {
        if (order == null) {
            return -1L;
        }
        Long customerId = getCustomerId(order.getCustomer());
        Long productId = getProductId(order.getProduct());
        Long csrId = getCsrId(order.getCsr());
        Long statusId = null;
        if (order.getStatus() != null) {
            statusId = order.getStatus().getId();
        }

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_DATE_FINISH, order.getDate())
                .addValue(PARAM_ORDER_PREFERRED_DATE, order.getPreferedDate())
                .addValue(PARAM_ORDER_STATUS, statusId)
                .addValue(PARAM_CUSTOMER_ID, customerId)
                .addValue(PARAM_PRODUCT_ID, productId)
                .addValue(PARAM_CSR_ID, csrId);

        KeyHolder keys = new GeneratedKeyHolder();
        int affectedRows = affectedRows = namedJdbcTemplate.update(SQL_CREATE_ORDER, params, keys);

        Long newId = -1L;
        if (affectedRows > 0) {
            newId = (Long) keys.getKeys().get(PARAM_ORDER_ID);
            order.setId(newId);
            log.info("Order with id: " + newId + " is successfully created.");
            return newId;
        } else {
            log.error("Order doesn't created.");
            return newId;
        }

//        Long id = simpleInsert.executeAndReturnKey(params).longValue();
//        order.setId(id);
//        log.info("Order with id: " + id + " is successfully created.");
//        return id;
    }

    private Long getCustomerId(User customer) {
        if (customer == null) {
            return null;
        }
        Long customerId = customer.getId();
        if (customerId == null || customerId <= 0) {
            log.info("Start creating customer.");
            customerId = userDao.create(customer);
        }
        return customerId;
    }

    private Long getProductId(Product product) {
        if (product == null) {
            return null;
        }
        Long productId = product.getId();
        if (productId == null || productId <= 0) {
            productId = productDao.create(product);
        }
        return productId;
    }

    private Long getCsrId(User csr) {
        if (csr == null) {
            return null;
        }
        Long csrId = csr.getId();
        if (csrId == null || csrId <= 0) {
            csrId = userDao.create(csr);
        }
        return csrId;
    }

    @Override
    public Long update(Order order) {
        if (order == null) {
            return -1L;
        }
        Long customerId = getCustomerId(order.getCustomer());
        Long productId = getProductId(order.getProduct());
        Long csrId = getCsrId(order.getCsr());
        Long statusId = null;
        if (order.getStatus() != null) {
            statusId = order.getStatus().getId();
        }

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_ID, order.getId())
                .addValue(PARAM_ORDER_DATE_FINISH, order.getDate())
                .addValue(PARAM_ORDER_PREFERRED_DATE, order.getPreferedDate())
                .addValue(PARAM_ORDER_STATUS, statusId)
                .addValue(PARAM_CUSTOMER_ID, customerId)
                .addValue(PARAM_PRODUCT_ID, productId)
                .addValue(PARAM_CSR_ID, csrId);

        KeyHolder keys = new GeneratedKeyHolder();
        long affectedRows = namedJdbcTemplate.update(SQL_UPDATE_ORDER, params, keys);

        if (affectedRows > 0) {
            log.info("Order with id: " + order.getId() + " is successfully updated.");
            return affectedRows;
        } else {
            log.error("Order doesn't updated.");
            return affectedRows;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id == null || id <= 0) {
            return -1L;
        }
        SqlParameterSource params = new MapSqlParameterSource().addValue(PARAM_ORDER_ID, id);

        long affectedRows = namedJdbcTemplate.update(SQL_DELETE_ORDER, params);

        if (affectedRows > 0) {
            log.info("Order with id: " + id + " is successfully deleted.");
            return affectedRows;
        } else {
            log.error("Order doesn't deleted.");
            return affectedRows;
        }
    }

    @Override
    public Long delete(Order order) {
        if (order == null || order.getId() == null || order.getId() <= 0) {
            return -1L;
        }
        SqlParameterSource params = new MapSqlParameterSource().addValue(PARAM_ORDER_ID, order.getId());

        long affectedRows = namedJdbcTemplate.update(SQL_DELETE_ORDER, params);

        if (affectedRows > 0) {
            log.info("Order with id: " + order.getId() + " is successfully deleted.");
            return affectedRows;
        } else {
            log.error("Order doesn't deleted.");
            return affectedRows;
        }
    }

    @Override
    public Order findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_ID, id);
        List<Order> allOrder = namedJdbcTemplate.query(SQL_FIND_ORDER_BY_ID, params, new HistoryWithDetailExtractor());
        if(allOrder == null)
            return null;
        return allOrder.get(0);
    }
    
    @Override
    public List<Order> findAllByDateFinish(LocalDate date) {
        if (date == null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_DATE_FINISH, date);

        List<Order> allOrder = namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_DATE_FINISH, params, new HistoryWithDetailExtractor());
        return allOrder;
    }

    @Override
    public List<Order> findAllByPreferredDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_PREFERRED_DATE, date);

        List<Order> allOrder = namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_PREFERRED_DATE, params, new HistoryWithDetailExtractor());
        return allOrder;
    }

    @Override
    public List<Order> findAllByProductId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_ID, id);
        List<Order> allOrder = namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_PRODUCT_ID, params, new HistoryWithDetailExtractor());
        return allOrder;
    }

    @Override
    public List<Order> findAllByCustomerId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_CUSTOMER_ID, id);
        List<Order> allOrder = namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_CUSTOMER_ID, params, new HistoryWithDetailExtractor());
        return allOrder;
    }

    @Override
    public List<Order> findAllByCsrId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_CSR_ID, id);
        List<Order> allOrder = namedJdbcTemplate.query(SQL_FIND_ALL_ORDER_BY_CSR_ID, params, new HistoryWithDetailExtractor());
        return allOrder;
    }

    

    private final class HistoryWithDetailExtractor implements ResultSetExtractor<List<Order>> {

        @Override
        public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<Order> allOrder = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong(PARAM_ORDER_ID));
                order.setDate(rs.getDate(PARAM_ORDER_DATE_FINISH).toLocalDate());
                java.sql.Date d = rs.getDate(PARAM_ORDER_PREFERRED_DATE);
                LocalDate date = null;
                if(d != null)
                    date = d.toLocalDate();
                order.setPreferedDate(date);
                
                long statusId = rs.getLong(PARAM_ORDER_STATUS);
                if (statusId > 0) {
                    Status status = Status.getStatusByID(statusId);  
                    if (status instanceof OrderStatus) {
                        order.setStatus((OrderStatus) status);
                    }
                }
                           
                Long customerId = rs.getLong(PARAM_CUSTOMER_ID);
                if (customerId > 0) {
                    User customer = userDao.findById(customerId);
                    order.setCustomer(customer);
                }

                Long productId = rs.getLong(PARAM_PRODUCT_ID);
                if (productId > 0) {
                    Product product = productDao.findById(productId);
                    order.setProduct(product);
                }
                
                Long csrId = rs.getLong(PARAM_CSR_ID);
                if (csrId > 0) {
                    User csr = userDao.findById(csrId);
                    order.setCsr(csr);
                }
                
                allOrder.add(order);
            }
            return allOrder;
        }
    }
}
