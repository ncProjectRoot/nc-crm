package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Karpunets
 * @since 01.06.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoImplTest {
    
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    private User userCreated;
    private Order orderCreated;
    private Product productCreated;

//    @Before
    public void create() throws Exception {
        userCreated = new User();
        userCreated.setPassword("test password");
        userCreated.setFirstName("test first name");
        userCreated.setMiddleName("test middle name");
        userCreated.setEmail("test email");
        userCreated.setEnable(false);
        userCreated.setAccountNonLocked(false);
        userCreated.setContactPerson(false);
        userCreated.setUserRole(UserRole.ROLE_CUSTOMER);

        orderCreated = new Order();
        orderCreated.setStatus(OrderStatus.NEW);
        orderCreated.setDate(LocalDateTime.now());
        orderCreated.setPreferedDate(LocalDateTime.now());
        orderCreated.setCustomer(userCreated);
        orderCreated.setCsr(userCreated);

        productCreated = new Product();
        productCreated.setTitle("test product title");
        productCreated.setStatus(ProductStatus.OUTDATED);
        orderCreated.setProduct(productCreated);

        assertNotNull(orderDao.create(orderCreated));
    }

    @Test
    public void random() throws Exception {
        OrderStatus[] orderStatuses = OrderStatus.values();
        Random random = new Random();

        for (int i = 0; i < 50000; i++) {
            orderCreated = new Order();
            orderCreated.setStatus(orderStatuses[random.nextInt(orderStatuses.length)]);
            orderCreated.setDate(LocalDateTime.of(2010 + random.nextInt(7), 1 + random.nextInt(12),1 + random.nextInt(25), random.nextInt(24), random.nextInt(60)));
            orderCreated.setPreferedDate(LocalDateTime.of(2010 + random.nextInt(7), 1 + random.nextInt(12),1 + random.nextInt(25), random.nextInt(24), random.nextInt(60)));

            userCreated = new User();
            userCreated.setPassword("test password" + i);
            userCreated.setFirstName("test first name" + i);
            userCreated.setMiddleName("test middle name" + i);
            userCreated.setEmail("test email" + i);
            userCreated.setEnable(false);
            userCreated.setAccountNonLocked(false);
            userCreated.setContactPerson(false);
            userCreated.setUserRole(UserRole.ROLE_CUSTOMER);

            orderCreated.setCustomer(userCreated);
            if (random.nextBoolean()) {
                orderCreated.setCsr(userCreated);
            }
            Product newProduct = new Product();
            newProduct.setId((long) random.nextInt(1000));
            orderCreated.setProduct(newProduct);
            assertNotNull(orderDao.create(orderCreated));
        }
    }

    @Test
    public void findAndUpdate() throws Exception {
        Order orderFoundById = orderDao.findById(orderCreated.getId());
        assertEquals(orderCreated.getStatus(), orderFoundById.getStatus());

        List<Order> ordersFoundByProductId = orderDao.findAllByProductId(productCreated.getId());
        assertEquals(orderCreated.getId(), ordersFoundByProductId.get(0).getId());

        List<Order> ordersFoundByCsrId = orderDao.findAllByCsrId(userCreated.getId());
        assertEquals(orderCreated.getId(), ordersFoundByCsrId.get(0).getId());

        List<Order> ordersFoundByCustomerId = orderDao.findAllByCustomerId(userCreated.getId());
        assertEquals(orderCreated.getId(), ordersFoundByCustomerId.get(0).getId());

        List<Order> ordersFoundByDateFinish = orderDao.findAllByDateFinish(orderCreated.getDate().toLocalDate());
        assertEquals(orderCreated.getId(), ordersFoundByDateFinish.get(0).getId());

        List<Order> ordersFoundByPreferredDate = orderDao.findAllByPreferredDate(orderCreated.getPreferedDate().toLocalDate());
        assertEquals(orderCreated.getId(), ordersFoundByPreferredDate.get(0).getId());

        orderCreated.setStatus(OrderStatus.DISABLED);
        assertEquals(orderDao.update(orderCreated), orderCreated.getId());
    }

//    @After
    public void delete() throws Exception {
        long affectedRows = orderDao.delete(orderCreated);
        assertEquals(affectedRows, 1L);
        productDao.delete(productCreated);
        userDao.delete(userCreated);
    }

}
