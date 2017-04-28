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
public interface ProductDao extends CrudDao<Product>{
    Product findByTitle(Long id);

    List<Product> findAllByGroupId(Long id);
}
