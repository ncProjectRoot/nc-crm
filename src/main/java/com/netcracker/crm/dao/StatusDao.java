/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Status;

/**
 *
 * @author YARUS
 */
public interface StatusDao {
    long create(Status order);

    long update(Status order);

    long delete(Long id);

    Status findById(Long id);

    Status findByName(String name);
}
