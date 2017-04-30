package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 30.04.2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintDaoImplTest {

    private Complaint complaint;

    @Autowired
    private ComplaintDao complaintDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Before
    public void create() throws Exception {
        complaint = new Complaint();
        complaint.setTitle("test title complaint");
        complaint.setMessage("test message complaint");
        complaint.setStatus(ComplaintStatus.OPEN);
        complaint.setDate(LocalDate.of(1967, 06, 22));
        complaint.setCustomer(userDao.findById(1L));
        complaint.setOrder(orderDao.findById(1L));
        Long id = complaintDao.create(complaint);
        assertNotNull(id);
    }

    @Test
    public void update() throws Exception {
        complaint.setMessage("update test message complaint");
        assertEquals(complaintDao.update(complaint), complaint.getId());
    }

    @Test
    public void findById() throws Exception {
        Complaint complaint = complaintDao.findById(this.complaint.getId());
        assertNotNull(complaint);
        assertEquals(complaint.getId(), this.complaint.getId());
    }

    @Test
    public void findByTitle() throws Exception {
        List<Complaint> complaintList = complaintDao.findByTitle(this.complaint.getTitle());
        boolean assertListContains = false;
        for (Complaint complaint : complaintList) {
            if (complaint.getId().equals(this.complaint.getId())) {
                assertListContains = true;
            }
        }
        assertTrue(assertListContains);
    }

    @Test
    public void findAllByDate() throws Exception {
        List<Complaint> complaintList = complaintDao.findAllByDate(this.complaint.getDate());
        boolean assertListContains = false;
        for (Complaint complaint : complaintList) {
            if (complaint.getId().equals(this.complaint.getId())) {
                assertListContains = true;
            }
        }
        assertTrue(assertListContains);
    }

}