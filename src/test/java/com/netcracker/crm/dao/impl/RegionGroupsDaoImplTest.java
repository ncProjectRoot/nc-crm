package com.netcracker.crm.dao.impl;


import com.netcracker.crm.dao.RegionGroupsDao;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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


    @Test
    public void create() throws Exception {
        Region region = new Region();
        region.setId(2L);
        Group group1 = new Group();
        group1.setId(1L);

        regionGroupsDao.create(region, group1);
    }

    @Test
    public void delete() throws Exception {
        regionGroupsDao.delete(2L, 1L);
    }

    @Test
    public void findGroupsByRegion() throws Exception {
        Region region = new Region();
        region.setId(1L);
        List<Group> groups = regionGroupsDao.findGroupsByRegion(region);
        for (Group group : groups) {
            System.out.println(group);
        }
    }

    @Test
    public void findRegionsByGroup() throws Exception {
        Group group = new Group();
        group.setId(1L);
        List<Region> regions = regionGroupsDao.findRegionsByGroup(group);
        for (Region region : regions) {
            System.out.println(region);
        }
    }

}