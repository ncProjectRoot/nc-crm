/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author YARUS
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoImplTest {
    
    @Autowired
    private OrderDao orderDao;
    
    public OrderDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class OrderDaoImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Order order = new Order();
        order.setDate(LocalDate.now());
        order.setPreferedDate(LocalDate.MIN);        
        order.setStatus(OrderStatus.NEW);               
        order.setCustomer(createCustomer());
        Product product = new Product();
        product.setId(1L);
        order.setProduct(product);
        long expResult = 1L;
        long result = orderDao.create(order);
        if(result <= 0)
            fail("result <= 0");
        
    }
    
    
    private User createCustomer(){
        User customer = new User();
        customer.setEmail("yarus@mail.ru");
        customer.setFirstName("Yarus");
        customer.setMiddleName("Mus");
        customer.setEnable(true);
        customer.setUserRole(UserRole.ROLE_CUSTOMER);
        customer.setPassword("412asdf1");
        customer.setAccountNonLocked(true);
        customer.setContactPerson(true);
        return customer;
    }

    /**
     * Test of update method, of class OrderDaoImpl.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Order order = new Order();
        order.setId(1L);
        order.setDate(LocalDate.now());
        order.setPreferedDate(LocalDate.MIN);        
        order.setStatus(OrderStatus.PAUSED);
        User customer = new User();
        customer.setId(2L);
        order.setCustomer(customer);
        Product product = new Product();
        product.setId(1L);
        order.setProduct(product);
        
        long result = orderDao.update(order);
        assertNotEquals(result, -1);
        if(result <= 0)
            fail("result <= 0");
    }

//    /**
//     * Test of delete method, of class OrderDaoImpl.
//     */
//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        Long id = null;
//        OrderDaoImpl instance = new OrderDaoImpl();
//        long expResult = 0L;
//        long result = instance.delete(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of findById method, of class OrderDaoImpl.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        Order result = orderDao.findById(1L);
        assertNotNull(result);
        if(result.getStatus().getId() != 2L)
            fail("status_id != 2");
        
        assertEquals(result.getDate(), LocalDate.now());
    }

//    /**
//     * Test of findAllByDate method, of class OrderDaoImpl.
//     */
//    @Test
//    public void testFindAllByDate() {
//        System.out.println("findAllByDate");
//        Date date = null;
//        OrderDaoImpl instance = new OrderDaoImpl();
//        List<Order> expResult = null;
//        List<Order> result = instance.findAllByDate(date);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAllByProductId method, of class OrderDaoImpl.
//     */
//    @Test
//    public void testFindAllByProductId() {
//        System.out.println("findAllByProductId");
//        Long id = null;
//        OrderDaoImpl instance = new OrderDaoImpl();
//        List<Order> expResult = null;
//        List<Order> result = instance.findAllByProductId(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAllByCustomerId method, of class OrderDaoImpl.
//     */
//    @Test
//    public void testFindAllByCustomerId() {
//        System.out.println("findAllByCustomerId");
//        Long id = null;
//        OrderDaoImpl instance = new OrderDaoImpl();
//        List<Order> expResult = null;
//        List<Order> result = instance.findAllByCustomerId(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAllByCsrId method, of class OrderDaoImpl.
//     */
//    @Test
//    public void testFindAllByCsrId() {
//        System.out.println("findAllByCsrId");
//        Long id = null;
//        OrderDaoImpl instance = new OrderDaoImpl();
//        List<Order> expResult = null;
//        List<Order> result = instance.findAllByCsrId(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
