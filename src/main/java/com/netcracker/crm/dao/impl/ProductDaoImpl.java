/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.Product;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author YARUS
 */
@Repository
public class ProductDaoImpl implements ProductDao{

    @Override
    public Product findByTitle(Long id) {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Product> findAllByGroupId(Long id) {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long create(Product object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long update(Product object) {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long delete(Long id) {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long delete(Product object) {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product findById(Long id) {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
