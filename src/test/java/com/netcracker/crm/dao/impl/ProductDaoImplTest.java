package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.ProductStatus;
import org.junit.After;
import org.junit.Before;
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
    private DiscountDao discountDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private ProductDao productDao;

    private Discount discountCreated;
    private Group groupCreated;
    private Product productCreated;

    @Before
    public void create() throws Exception {
        discountCreated = new Discount();
        discountCreated.setTitle("test title discount");
        discountCreated.setPercentage(60.4);
        discountCreated.setDescription("test description discount");
        discountCreated.setActive(false);

        groupCreated = new Group();
        groupCreated.setName("test name group");

        productCreated = new Product();
        productCreated.setTitle("test title product");
        productCreated.setDefaultPrice(7.7);
        productCreated.setStatus(ProductStatus.ACTUAL);
        productCreated.setDescription("test description product");
        productCreated.setDiscount(discountCreated);
        productCreated.setGroup(groupCreated);

        assertNotNull(productDao.create(productCreated));
    }

    @Test
    public void findAndUpdate() throws Exception {
        Product productFoundById = productDao.findById(productCreated.getId());
        assertEquals(productCreated.getStatus(), productFoundById.getStatus());

        Product productFoundByTitle = productDao.findByTitle(productCreated.getTitle());
        assertEquals(productCreated.getId(), productFoundByTitle.getId());

        List<Product> productsFoundByGroupId = productDao.findAllByGroupId(groupCreated.getId());
        assertEquals(productCreated.getId(), productsFoundByGroupId.get(0).getId());

        productCreated.setStatus(ProductStatus.OUTDATED);
        assertEquals(productDao.update(productCreated), productCreated.getId());
    }

    @After
    public void delete() throws Exception {
        long affectedRows = productDao.delete(productCreated);
        assertEquals(affectedRows, 1L);
        discountDao.delete(discountCreated);
        groupDao.delete(groupCreated);
    }

}