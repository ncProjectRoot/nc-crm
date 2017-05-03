package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.ComplaintStatus;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.email.senders.ComplaintMailSender;
import com.netcracker.crm.email.senders.EmailMap;
import com.netcracker.crm.email.senders.EmailType;
import com.netcracker.crm.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 01.05.2017
 */

@Service
public class ComplaintServiceImpl implements ComplaintService{

    @Autowired
    private ComplaintDao complaintDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ComplaintMailSender mailSender;


    public Complaint persist(ComplaintDto dto) {
        Complaint complaint = convertToModel(dto);
        complaint.setDate(LocalDateTime.now());
        complaint.setStatus(ComplaintStatus.OPEN);
        Long id = complaintDao.create(complaint);
        complaint.setId(id);
        sendEmail(complaint);
        return complaint;
    }

    public List<Complaint> findByTitle(String title) {
        return complaintDao.findByTitle(title);
    }

    public List<Complaint> findByDate(LocalDate date) {
        return complaintDao.findAllByDate(date);
    }

    private Complaint convertToModel(ComplaintDto dto) {
        Complaint complaint = new Complaint();
        complaint.setTitle(dto.getTitle());
        complaint.setMessage(dto.getMessage());
        User customer = userDao.findById(dto.getCustomerId());
        complaint.setCustomer(customer);
        Order order = orderDao.findById(dto.getOrderId());
        complaint.setOrder(order);
        Long complaintId = dto.getId();
        if (complaintId != null) {
            complaint.setId(complaintId);
        }
        String status = dto.getStatus();
        if (status != null) {
            ComplaintStatus complaintStatus = null;
            if (status.equals(ComplaintStatus.OPEN.getName())) {
                complaintStatus = ComplaintStatus.OPEN;
            } else if (status.equals(ComplaintStatus.SOLVING.getName())) {
                complaintStatus = ComplaintStatus.SOLVING;
            } else if (status.equals(ComplaintStatus.CLOSED.getName())) {
                complaintStatus = ComplaintStatus.CLOSED;
            }
            complaint.setStatus(complaintStatus);
        }
        Long pmgId = dto.getPmgId();
        if (pmgId != null) {
            User pmg = userDao.findById(pmgId);
            complaint.setPmg(pmg);
        }
        String date = dto.getDate();
        if (date != null) {
            //TODO DATE CONVERT
        }
        return complaint;
    }

    private Complaint convertToDto(Complaint complaint) {
        ComplaintDto dto = new ComplaintDto();
        dto.setId(complaint.getId());
        dto.setTitle(complaint.getTitle());
        dto.setMessage(complaint.getMessage());
        dto.setStatus(complaint.getStatus().getName());
        dto.setCustomerId(complaint.getCustomer().getId());
        dto.setOrderId(complaint.getOrder().getId());
        dto.setDate(complaint.getDate().toString());
        if(complaint.getPmg()!=null){
            dto.setPmgId(complaint.getPmg().getId());
        }
        return complaint;
    }

    public List<Complaint> findByCustomerId(Long id){
        return complaintDao.findAllByCustomerId(id);
    }

    public Complaint findById(Long id){
        return complaintDao.findById(id);
    }

    private void sendEmail(Complaint complaint){
        EmailMap emailMap = new EmailMap(EmailType.COMPLAINT);
        emailMap.put("complaint", complaint);
        try {
            mailSender.send(emailMap);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
