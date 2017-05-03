package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Region;

import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 28.04.2017
 */
public interface RegionGroupsDao {

    Long create(Region region, Group group);

    Long delete(Region region, Group group);

    Long delete(Long idRegion, Long idGroup);

    List<Group> findGroupsByRegion(Region region);

    List<Region> findRegionsByGroup(Group group);

}
