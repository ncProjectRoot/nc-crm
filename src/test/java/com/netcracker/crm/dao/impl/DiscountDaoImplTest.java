package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
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
public class DiscountDaoImplTest {

    @Autowired
    private DiscountDao discountDao;

    @Test
    public void create() throws Exception {
        Discount discount = new Discount();
        discount.setTitle("Discount for internet");
        discount.setDescription("descr");
        discount.setPercentage(0.5);
        discount.setActive(true);
        long id = discountDao.create(discount);
        System.out.println("Discount with id " + id + " created");
    }

    @Test
    public void update() throws Exception {
        Discount discount = new Discount();
        discount.setTitle("Internet discount");
        discount.setId(1L);
        discount.setDescription("changed description");
        discount.setPercentage(0.5);
        discount.setActive(true);

        long id = discountDao.update(discount);
        System.out.println("Rows updated: " + id);
    }

    @Test
    public void delete() throws Exception {
        Discount discount = new Discount();
        discount.setId(1L);
        discountDao.delete(discount);
    }

    @Test
    public void findById() throws Exception {
        Discount discount = discountDao.findById(1L);
        System.out.println(discount);
    }

    @Test
    public void findByName() throws Exception {
        List<Discount> list = discountDao.findByTitle("DiSc");
        for (Discount discount : list) {
            System.out.println(discount);
        }
    }

    @Test
    public void getCount() throws Exception {
        System.out.println(discountDao.getCount());
    }


}