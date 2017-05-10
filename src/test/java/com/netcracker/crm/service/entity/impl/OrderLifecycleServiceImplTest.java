package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.service.entity.OrderLifecycleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by bpogo on 5/10/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderLifecycleServiceImplTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderLifecycleService lifecycleService;

    private User user;
    private Order order;
    private Product product;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setPassword("test password");
        user.setFirstName("test first name");
        user.setMiddleName("test middle name");
        user.setEmail(UUID.randomUUID().toString());
        user.setEnable(false);
        user.setAccountNonLocked(false);
        user.setContactPerson(false);
        user.setUserRole(UserRole.ROLE_CUSTOMER);

        this.order = new Order();
        this.order.setDate(LocalDateTime.now());
        this.order.setPreferedDate(LocalDateTime.now());
        this.order.setCustomer(user);
        this.order.setCsr(user);

        product = new Product();
        product.setTitle("test product title");
        product.setStatus(ProductStatus.ACTUAL);
        this.order.setProduct(product);

        userDao.create(user);
        productDao.create(product);
        orderDao.create(order);
    }

    @After
    public void tearDown() throws Exception {
        orderDao.delete(order.getId());
        productDao.delete(product.getId());
        userDao.delete(user.getId());
    }

    @Test
    public void createOrder() throws Exception {

    }

    @Test
    public void acceptOrder() throws Exception {
        boolean isAccepted = lifecycleService.acceptOrder(order.getId());
        Order acceptedOrder = orderDao.findById(order.getId());

        assertTrue(isAccepted);
        assertEquals(OrderStatus.PROCESSING, acceptedOrder.getStatus());
    }

    @Test
    public void activateOrder() throws Exception {
        lifecycleService.acceptOrder(order.getId());
        boolean isActivated = lifecycleService.activateOrder(order.getId());
        Order activatedOrder = orderDao.findById(order.getId());

        assertTrue(isActivated);
        assertEquals(OrderStatus.ACTIVE, activatedOrder.getStatus());
    }

    @Test
    public void pauseOrder() throws Exception {
        lifecycleService.acceptOrder(order.getId());
        lifecycleService.activateOrder(order.getId());
        boolean isPaused = lifecycleService.pauseOrder(order.getId());
        Order pausedOrder = orderDao.findById(order.getId());

        assertTrue(isPaused);
        assertEquals(OrderStatus.PAUSED, pausedOrder.getStatus());
    }

    @Test
    public void resumeOrderFromPausedToActive() throws Exception {
        lifecycleService.acceptOrder(order.getId());
        lifecycleService.activateOrder(order.getId());
        lifecycleService.pauseOrder(order.getId());
        boolean isActivated = lifecycleService.resumeOrder(order.getId());
        Order activatedOrder = orderDao.findById(order.getId());

        assertTrue(isActivated);
        assertEquals(OrderStatus.ACTIVE, activatedOrder.getStatus());
    }

    @Test
    public void cancelOrderFromActive() throws Exception {
        lifecycleService.acceptOrder(order.getId());
        lifecycleService.activateOrder(order.getId());
        boolean isDisabled = lifecycleService.cancelOrder(order.getId());
        Order activatedOrder = orderDao.findById(order.getId());

        assertTrue(isDisabled);
        assertEquals(OrderStatus.DISABLED, activatedOrder.getStatus());
    }

    @Test
    public void cancelOrderFromPaused() throws Exception {
        lifecycleService.acceptOrder(order.getId());
        lifecycleService.activateOrder(order.getId());
        lifecycleService.pauseOrder(order.getId());
        boolean isDisabled = lifecycleService.cancelOrder(order.getId());
        Order activatedOrder = orderDao.findById(order.getId());

        assertTrue(isDisabled);
        assertEquals(OrderStatus.DISABLED, activatedOrder.getStatus());
    }
}