package com.netcracker.crm.dao;


import com.netcracker.crm.domain.model.Group;

import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public interface GroupDao extends CrudDao<Group>{
    List<Group> findByName(String name);

    Long getCount();
}
