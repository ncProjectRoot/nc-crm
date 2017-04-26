package com.netcracker.crm.dao;


import com.netcracker.crm.domain.model.Group;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public interface GroupDao {

    long create(Group group);

    long update(Group group);

    long delete(Long id);

    Group findById(Long id);

    Group findByName(String name);
}
