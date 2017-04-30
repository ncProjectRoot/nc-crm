package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.ProductStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Karpunets
 * @since 30.04.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoImplTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private GroupDao groupDao;

    @Test
    public void create() throws Exception {
        Discount discount = new Discount();
        discount.setTitle("test title discount");
        discount.setPercentage(60.4);
        discount.setDescription("test description discount");
        discount.setActive(false);

        Product product = new Product();
        product.setTitle("test title product");
        product.setDefaultPrice(7.7);
        product.setStatus(ProductStatus.ACTUAL);
        product.setDescription("test description product");
        product.setDiscount(discount);
        product.setGroup(groupDao.findById(1L));

        Long id = productDao.create(product);
        assertNotNull(id);
    }

    @Test
    public void update() throws Exception {
        Product product = productDao.findById(1L);
        product.setDescription("update test description");
        assertEquals(productDao.update(product), product.getId());
    }

    @Test
    public void findById() throws Exception {
        Product product = productDao.findById(1L);
        assertEquals(product.getId(), new Long(1));
    }

    @Test
    public void findByTitle() throws Exception {
        Product product = productDao.findByTitle("test product");
        assertEquals(product.getId(), new Long(1));
    }

    @Test
    public void findAllByGroupId() throws Exception {
        List<Product> productList = productDao.findAllByGroupId(1L);
        assertNotEquals(productList.size(), 0);
    }

}