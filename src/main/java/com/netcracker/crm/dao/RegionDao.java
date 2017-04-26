package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Region;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public interface RegionDao {

    long create(Region region);

    long update(Region region);

    long delete(Long id);

    long delete(Region region);

    Region findById(Long id);

    Region findByName(String name);

}
