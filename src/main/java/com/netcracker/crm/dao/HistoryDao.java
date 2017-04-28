/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.History;
import java.util.Date;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface HistoryDao extends CrudDao<History> {
    List<History> findAllByDate(Date date);

    List<History> findAllByOrderId(Long id);
    
    List<History> findAllByComplaintId(Long id);
    
    List<History> findAllByProductId(Long id);
    
}
