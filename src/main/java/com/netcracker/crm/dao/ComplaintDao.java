/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Complaint;
import java.util.Date;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface ComplaintDao {
    long create(Complaint complaint);

    long update(Complaint complaint);

    long delete(Long id);

    Complaint findById(Long id);

    Complaint findByTitle(String title);
    
    List<Complaint> findAllByDate(Date date);
    
}
