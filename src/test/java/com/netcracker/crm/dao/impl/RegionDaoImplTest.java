package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.RegionDao;
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
 * @since 26.04.2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionDaoImplTest {

    @Autowired
    private RegionDao regionDao;

    private Region regionCreated;

    @Before
    public void create() throws Exception {
        regionCreated = new Region();
        regionCreated.setName("test region name");
        regionDao.create(regionCreated);
        assertNotNull(regionCreated.getId());
    }

    @Test
    public void findAndUpdateAndCount() throws Exception {
        Region regionFoundById = regionDao.findById(regionCreated.getId());
        assertEquals(regionCreated.getName(), regionFoundById.getName());

        List<Region> regionsFoundByName = regionDao.findByName(regionCreated.getName());
        assertEquals(regionCreated.getId(), regionsFoundByName.get(0).getId());

        regionCreated.setName("test update region name Region");
        assertEquals(regionDao.update(regionCreated), regionCreated.getId());

        assertEquals(regionDao.getCount(), new Long(1));
    }

    @After
    public void delete() throws Exception {
        long affectedRows = regionDao.delete(regionCreated);
        assertEquals(affectedRows, 1L);
    }

}