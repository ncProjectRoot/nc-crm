package com.netcracker.crm.dao.impl;


import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.dao.RegionGroupsDao;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Region;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.*;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 28.04.2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionGroupsDaoImplTest {

    @Autowired
    private RegionGroupsDao regionGroupsDao;
    @Autowired
    private RegionDao regionDao;
    @Autowired
    private GroupDao groupDao;

    private Region regionCreated;
    private Group groupCreated;

    @Before
    public void create() throws Exception {
        regionCreated = new Region();
        regionCreated.setName("test region name");
        groupCreated = new Group();
        groupCreated.setName("test group name");

        Long idRegionGroups = regionGroupsDao.create(regionCreated, groupCreated);
        assertNotNull(idRegionGroups);
        assertNotEquals(idRegionGroups, new Long(-1));
    }

    @Test
    public void find() throws Exception {
        List<Group> groups = regionGroupsDao.findGroupsByRegion(regionCreated);
        assertEquals(groups.get(0).getId(), groupCreated.getId());

        List<Region> regions = regionGroupsDao.findRegionsByGroup(groupCreated);
        assertEquals(regions.get(0).getId(), regionCreated.getId());
    }

    @After
    public void delete() throws Exception {
        regionGroupsDao.delete(regionCreated.getId(), groupCreated.getId());
        regionDao.delete(regionCreated);
        groupDao.delete(groupCreated);
    }

}