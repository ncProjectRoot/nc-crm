package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Region;

import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public interface RegionDao extends CrudDao<Region> {

    List<Region> findByName(String name);

    Long getCount();

}
