/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.StatusDao;
import static com.netcracker.crm.dao.impl.sql.HistorySqlQuery.*;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.Status;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author YARUS
 */
@Repository
public class HistoryDaoImpl implements HistoryDao{

    private static final Logger log = LoggerFactory.getLogger(HistoryDaoImpl.class);
    
    @Autowired
    private ComplaintDao complaintDao;
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private StatusDao statusDao;
    
    private SimpleJdbcInsert simpleInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_HISTORY_TABLE)
                .usingGeneratedKeyColumns(PARAM_HISTORY_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    @Override
    public long create(History history) {        
        Long orderId = getOrderId(history.getOrder());
        Long complaintId = getComplaintId(history.getComplaint());
        Long productId = getProductId(history.getProduct());
        
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_DATE_CHANGE_STATUS, history.getDateChangeStatus())
                .addValue(PARAM_HISTORY_DESC_CHANGE_STATUS, history.getDescChangeStatus())
                .addValue(PARAM_HISTORY_OLD_STATUS_ID, history.getOldStatus().getId())
                .addValue(PARAM_HISTORY_ORDER_ID, orderId)
                .addValue(PARAM_HISTORY_COMPLAINT_ID, complaintId)
                .addValue(PARAM_HISTORY_PRODUCT_ID, productId)
                ;
        
        KeyHolder keys = new GeneratedKeyHolder();
        int affectedRows = namedJdbcTemplate.update(SQL_CREATE_HISTORY, params, keys);
        
        Long newId = -1L;
        if (affectedRows > 0) {
            newId = (Long) keys.getKeys().get(PARAM_HISTORY_ID);
            history.setId(newId);
            log.info("History with id: " + newId + " is successfully created.");
            return newId;
        } else {
            log.error("History doesn't created.");
            return newId;
        }
    }    
    
    private Long getOrderId(Order order) {
        Long orderId = order.getId();
        if (orderId != null) {
            return orderId;
        }
        orderId = orderDao.create(order);
        return orderId;
    }
    
    private Long getComplaintId(Complaint complaint) {
        Long complaintId = complaint.getId();
        if (complaintId != null) {
            return complaintId;
        }
        complaintId = complaintDao.create(complaint);
        return complaintId;
    }
    
    private Long getProductId(Product product) {
        Long productId = product.getId();
        if (productId != null) {
            return productId;
        }
        productId = productDao.create(product);
        return productId;
    }
    
    @Override
    public boolean update(History history) {
        Long orderId = getOrderId(history.getOrder());
        Long complaintId = getComplaintId(history.getComplaint());
        Long productId = getProductId(history.getProduct());
        
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_ID, history.getId())
                .addValue(PARAM_HISTORY_DATE_CHANGE_STATUS, history.getDateChangeStatus())
                .addValue(PARAM_HISTORY_DESC_CHANGE_STATUS, history.getDescChangeStatus())
                .addValue(PARAM_HISTORY_OLD_STATUS_ID, history.getOldStatus().getId())
                .addValue(PARAM_HISTORY_ORDER_ID, orderId)
                .addValue(PARAM_HISTORY_COMPLAINT_ID, complaintId)
                .addValue(PARAM_HISTORY_PRODUCT_ID, productId)
                ;
        
        KeyHolder keys = new GeneratedKeyHolder();
        int affectedRows = namedJdbcTemplate.update(SQL_UPDATE_HISTORY, params, keys);
                
        if (affectedRows > 0) {           
            log.info("History with id: " + history.getId() + " is successfully updated.");
            return true;
        } else {
            log.error("History doesn't updated.");
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue(PARAM_HISTORY_ID, id);
        
        int affectedRows = namedJdbcTemplate.update(SQL_DELETE_HISTORY, params);
                
        if (affectedRows > 0) {                        
            log.info("History with id: " + id + " is successfully deleted.");
            return true;
        } else {
            log.error("History doesn't deleted.");
            return false;
        }
    }

    @Override
    public History findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<History> findAllByDate(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<History> findAllByOrderId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<History> findAllByComplaintId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<History> findAllByProductId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
