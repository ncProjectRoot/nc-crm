package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Complaint;

import java.time.LocalDate;
import java.util.List;

public interface ComplaintDao extends CrudDao<Complaint>{

    List<Complaint> findByTitle(String title);
    
    List<Complaint> findAllByDate(LocalDate date);
    
}
