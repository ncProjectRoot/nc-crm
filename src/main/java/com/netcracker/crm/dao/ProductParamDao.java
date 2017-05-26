/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.ProductParam;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface ProductParamDao extends CrudDao<ProductParam>{
    List<ProductParam> findByParamName(String paramName);
    
    List<ProductParam> findAllByProductId(Long id);
    
    
}
