package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.History;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface HistoryDao extends CrudDao<History> {
    List<History> findAllByDate(LocalDate date);

    List<History> findAllByOrderId(Long id);
    
    List<History> findAllByComplaintId(Long id);
    
    List<History> findAllByProductId(Long id);
    
}
