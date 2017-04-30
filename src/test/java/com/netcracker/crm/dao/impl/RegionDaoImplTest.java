package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.domain.model.Discount;
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
 * @since 26.04.2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionDaoImplTest {

    @Autowired
    private RegionDao regionDao;

    @Test
    public void create() throws Exception {
        Discount discount = new Discount();
        discount.setTitle("Discount_for_Region_Ukraine");
        discount.setDescription("descr");
        discount.setPercentage(0.5);
        discount.setActive(false);

        Region region = new Region();
        region.setDiscount(discount);
        region.setName("Ukraine");

        regionDao.create(region);

    }

    @Test
    public void update() throws Exception {
        Discount discount = new Discount();
        discount.setId(6L);

        Region region = new Region();
        region.setId(1L);
        region.setDiscount(discount);
        region.setName("Germany");

        regionDao.update(region);
    }

    @Test
    public void delete() throws Exception {
        Region region = new Region();
        region.setId(11L);

        System.out.println(regionDao.delete(region));
    }

    @Test
    public void findById() throws Exception {
        Region region = regionDao.findById(10L);
        System.out.println(region);
    }

    @Test
    public void findByName() throws Exception {
        List<Region> list = regionDao.findByName("");
        for (Region region : list) {
            System.out.println(region);
        }
    }

    @Test
    public void getCount() throws Exception {
        System.out.println(regionDao.getCount());
    }

}