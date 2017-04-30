/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
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
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private UserDao userDao;
    
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

    /**
     * Test of delete method, of class OrderDaoImpl.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");  
        /////////////////////////Create test customer///////////////////////////
        User customer = new User();
        customer.setEmail("yarus32@mail.ru");
        customer.setFirstName("Yarus32");
        customer.setMiddleName("Mus32");
        customer.setEnable(true);
        customer.setUserRole(UserRole.ROLE_CUSTOMER);
        customer.setPassword("412asdf132");
        customer.setAccountNonLocked(true);
        customer.setContactPerson(true);
        //////////////////////////Create test order///////////////////////////
        Order order = new Order();
        order.setDate(LocalDate.now());
        order.setPreferedDate(LocalDate.MIN);        
        order.setStatus(OrderStatus.NEW);               
        order.setCustomer(customer);
        Product product = new Product();
        product.setId(1L);
        order.setProduct(product);        
        long id = orderDao.create(order);
        ///////////////////////////Delete test customer///////////////////////////
        long result = orderDao.delete(id);
        assertNotEquals(result, -1);
        if(result <= 0)
            fail("result <= 0");
    }

    /**
     * Test of findById method, of class OrderDaoImpl.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        Order result = orderDao.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getDate());            
        if(result.getStatus().getId() != 5L)
            fail("status_id != 5");
        
        assertEquals(result.getDate(), LocalDate.of(1994, 10, 10));
    }

    /**
     * Test of findAllByDate method, of class OrderDaoImpl.
     */
    @Test
    public void testFindAllByDate() {
        System.out.println("findAllByDate");
        List<Order> result = orderDao.findAllByDateFinish(LocalDate.of(1994, 10, 10));
        assertNotNull(result);
        if(result.size() <= 0)
            fail("list is empty");        
    }

    /**
     * Test of findAllByProductId method, of class OrderDaoImpl.
     */
    @Test
    public void testFindAllByProductId() {
        System.out.println("findAllByProductId");
        List<Order> result = orderDao.findAllByProductId(2L);
        assertNotNull(result);
        if(result.size() <= 0)
            fail("list is empty");  
        assertNotNull(productDao.findById(result.get(0).getId()));
    }

    /**
     * Test of findAllByCustomerId method, of class OrderDaoImpl.
     */
    @Test
    public void testFindAllByCustomerId() {
        System.out.println("findAllByCustomerId");
        List<Order> result = orderDao.findAllByCustomerId(2L);
        assertNotNull(result);
        if(result.size() <= 0)
            fail("list is empty");  
        assertNotNull(userDao.findById(result.get(0).getId()));
    }

    /**
     * Test of findAllByCsrId method, of class OrderDaoImpl.
     */
    @Test
    public void testFindAllByCsrId() {
        System.out.println("findAllByCsrId");
        List<Order> result = orderDao.findAllByCustomerId(2L);
        assertNotNull(result);
        if(result.size() <= 0)
            fail("list is empty");
        assertNotNull(userDao.findById(result.get(0).getId()));
    }
    
}
