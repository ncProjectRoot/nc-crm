/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface OrderDao extends CrudDao<Order>{
    List<Order> findAllByDateFinish(LocalDate date);
    
    List<Order> findAllByPreferredDate(LocalDate date);
            
    List<Order> findAllByProductId(Long id);
    
    List<Order> findAllByCustomerId(Long id);
    
    List<Order> findAllByCsrId(Long id);

    List<Order> findAllByCsrIdAndCustomerId(Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

    List<Order> findAllByCsrIdAndArrayOfCustomerId(Long csr_id, List<Long> customer_id_list, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

}
