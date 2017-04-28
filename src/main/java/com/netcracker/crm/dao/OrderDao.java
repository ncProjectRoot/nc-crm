/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Order;
import java.util.Date;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface OrderDao {
    long create(Order order);

    long update(Order order);

    long delete(Long id);

    Order findById(Long id);

    List<Order> findAllByDate(Date date);
    
    List<Order> findAllByProductId(Long id);
    
    List<Order> findAllByCustomerId(Long id);
    
    List<Order> findAllByCsrId(Long id);
}
