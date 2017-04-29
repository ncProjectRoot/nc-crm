/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
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
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.Status;
import com.netcracker.crm.domain.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author YARUS
 */
@Repository
public class OrderDaoImpl implements OrderDao{

    private static final Logger log = LoggerFactory.getLogger(OrderDaoImpl.class);
    
    @Autowired
    private static UserDao userDao;
    
    @Autowired
    private static ProductDao productDao;
    
    private SimpleJdbcInsert simpleInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_ORDER_TABLE)
                .usingGeneratedKeyColumns(PARAM_ORDER_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    
    @Override
    public Long create(Order order) {
        if(order == null)
            return -1L;
        Long statusId = getStatusId(order.getStatus());
        Long customerId = getCustomerId(order.getCustomer());
        Long productId = getProductId(order.getProduct());
        Long csrId = getCsrId(order.getCsr());
        
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_ORDER_DATE, order.getDate())
                .addValue(PARAM_ORDER_STATUS, statusId)
                .addValue(PARAM_CUSTOMER_ID, customerId)
                .addValue(PARAM_PRODUCT_ID, productId)
                .addValue(PARAM_CSR_ID, csrId)
                ;
        
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
    }
    
    private Long getStatusId(Status status) {
        if(status == null)
            return null;
        Long statusId = status.getId();
        return statusId;
    }
    
    private Long getCustomerId(User customer) {
        if(customer == null)
            return null;
        Long customerId = customer.getId();
        if (customerId != null && customerId > 0) {
            return customerId;
        }
        customerId = userDao.create(customer);
        return customerId;
    }
    
    private Long getProductId(Product product) {
        if(product == null)
            return null;
        Long productId = product.getId();
        if (productId != null) {
            return productId;
        }
        productId = productDao.create(product);
        return productId;
    }
    
    private Long getCsrId(User csr){
        if(csr == null)
            return null;
        Long csrId = csr.getId();
        if (csrId != null) {
            return csrId;
        }
        csrId = userDao.create(csr);
        return csrId;
    }

    @Override
    public List<Order> findAllByDate(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> findAllByProductId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> findAllByCustomerId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> findAllByCsrId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public Long update(Order object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long delete(Order object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order findById(Long id) {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
