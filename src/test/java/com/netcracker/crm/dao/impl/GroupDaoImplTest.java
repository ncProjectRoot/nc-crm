package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
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
public class GroupDaoImplTest {
    @Autowired
    private GroupDao groupDao;

    @Test
    public void create() throws Exception {
        Discount discount = new Discount();
        discount.setTitle("Discount_for_Group");
        discount.setDescription("descr");
        discount.setPercentage(0.5);
        discount.setActive(true);

        Group group = new Group();
        group.setDiscount(discount);
        group.setName("Internet");

        groupDao.create(group);
    }

    @Test
    public void update() throws Exception {
        Discount discount = new Discount();
        discount.setId(5L);

        Group group = new Group();
        group.setId(3L);
        group.setDiscount(discount);
        group.setName("Service_Internet");

        System.out.println(groupDao.update(group));
    }

    @Test
    public void delete() throws Exception {
        Group group = new Group();
        group.setId(1L);

        System.out.println(groupDao.delete(group));
    }

    @Test
    public void findById() throws Exception {
        System.out.println(groupDao.findById(3L));
    }

    @Test
    public void findByName() throws Exception {
       List<Group> list =  groupDao.findByName("teR");
        for (Group group : list) {
            System.out.println(group);
        }
    }

    @Test
    public void getCount() throws Exception {
        System.out.println(groupDao.getCount());
    }



}