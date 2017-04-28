/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Product;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface ProductDao {
    long create(Product product);

    long update(Product product);

    long delete(Long id);

    Product findById(Long id);
    
    Product findByTitle(Long id);

    List<Product> findAllByGroupId(Long id);
}
