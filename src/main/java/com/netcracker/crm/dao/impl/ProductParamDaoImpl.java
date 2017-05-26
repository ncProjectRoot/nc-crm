package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.ProductParamDao;
import com.netcracker.crm.domain.model.ProductParam;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 *
 * @author YARUS
 */
public class ProductParamDaoImpl implements ProductParamDao{

    private static final Logger log = LoggerFactory.getLogger(DiscountDaoImpl.class);

    private ProductDao discountDao;
    
    private SimpleJdbcInsert productInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private ProductParamWithDetailExtractor productParamWithDetailExtractor;
    
    @Override
    public ProductParam findByParamName(String paramName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProductParam> findAllByProductId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long create(ProductParam object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long update(ProductParam object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long delete(ProductParam object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductParam findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static final class ProductParamWithDetailExtractor implements ResultSetExtractor<List<ProductParam>> {

        private ProductDao productDao;        

        public ProductParamWithDetailExtractor(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        public List<ProductParam> extractData(ResultSet rs) throws SQLException, DataAccessException {
            
            return null;
        }
    }
}
