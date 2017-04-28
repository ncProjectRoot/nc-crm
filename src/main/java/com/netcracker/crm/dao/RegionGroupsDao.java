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

    long create(Region region, Group group);

    long delete(Region region, Group group);

    long delete(Long idRegion, Long idGroup);

    List<Group> findGroupsByRegion(Region region);

    List<Region> findRegionsByGroup(Group group);

}
