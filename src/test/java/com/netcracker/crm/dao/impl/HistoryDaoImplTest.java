/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.netcracker.crm.domain.model.OrderStatus;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class HistoryDaoImplTest {
    
    @Autowired
    private HistoryDao historyDao;
    
    public HistoryDaoImplTest() {
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
     * Test of create method, of class HistoryDaoImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        History history = new History();        
        history.setOldStatus(OrderStatus.NEW);
        Order ord = new Order();
        ord.setId(0l);
        history.setOrder(null);
        history.setProduct(null);
        history.setComplaint(null);
        history.setDateChangeStatus(LocalDate.MAX);
        history.setDescChangeStatus("Бо треба протестить");
                
        long expResult = 0L;
        long result = historyDao.create(history);
        assertNotEquals(result, -3);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of update method, of class HistoryDaoImpl.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        History history = new History();      
        history.setId(1L);
        history.setOldStatus(OrderStatus.ACTIVE);        
        history.setOrder(null);
        history.setProduct(null);
        history.setComplaint(null);
        history.setDateChangeStatus(LocalDate.MIN);
        history.setDescChangeStatus("Бо треба протестить22");
        
        long result = historyDao.update(history);
        assertNotEquals(result, -1);
        if(result <= 0)
            fail("result <= 0");
        
    }

//    /**
//     * Test of delete method, of class HistoryDaoImpl.
//     */
//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//               
//        long result = historyDao.delete(5L);
//        assertNotEquals(result, -1);
//        if(result <= 0)
//            fail("result <= 0");
//    }

    /**
     * Test of findById method, of class HistoryDaoImpl.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        
        History result = historyDao.findById(1L);
        assertNotNull(result);
        assertEquals(result.getDescChangeStatus(), "For test");
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d = df.parse("1994-10-10");
        } catch (ParseException ex) {
            Logger.getLogger(HistoryDaoImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(result.getDateChangeStatus().getYear(), d.getYear() + 1900);
        assertEquals(result.getDateChangeStatus().getMonthValue(), d.getMonth()+1);
        assertEquals(result.getDateChangeStatus().getDayOfMonth(), d.getDate());
        
    }

    /**
     * Test of findAllByDate method, of class HistoryDaoImpl.
     */
    @Test
    public void testFindAllByDate() {
        System.out.println("findAllByDate");
        LocalDate ldate = LocalDate.of(1994, 10, 10);
        
        List<History> result = historyDao.findAllByDate(ldate);
        assertNotNull(result);
        assertNotEquals(result.size(), 0);        
    }

    /**
     * Test of findAllByOrderId method, of class HistoryDaoImpl.
     */
    @Test
    public void testFindAllByOrderId() {
        System.out.println("findAllByOrderId");
        Long l = null;
        List<History> result = historyDao.findAllByOrderId(1L);
        assertNotNull(result);
        assertNotEquals(result.size(), 0);;
    }

    /**
     * Test of findAllByComplaintId method, of class HistoryDaoImpl.
     */
//    @Test
//    public void testFindAllByComplaintId() {
//        System.out.println("findAllByComplaintId");
//        List<History> result = historyDao.findAllByComplaintId(1L);
//        assertNotNull(result);
//        assertNotEquals(result.size(), 0);
//    }

    /**
     * Test of findAllByProductId method, of class HistoryDaoImpl.
     */
    @Test
    public void testFindAllByProductId() {
        System.out.println("findAllByProductId");
        List<History> result = historyDao.findAllByProductId(1L);
        assertNotNull(result);
        assertNotEquals(result.size(), 0);
    }
    
}
