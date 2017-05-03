package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.ComplaintStatus;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.dto.mapper.ComplaintMapper;
import com.netcracker.crm.email.senders.AbstractEmailSender;
import com.netcracker.crm.email.senders.ComplaintMailSender;
import com.netcracker.crm.email.senders.EmailMap;
import com.netcracker.crm.email.senders.EmailType;
import com.netcracker.crm.service.ComplaintService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ComplaintServiceImpl implements ComplaintService {
    private static final Logger log = LoggerFactory.getLogger(ComplaintServiceImpl.class);

    private ComplaintDao complaintDao;
    private OrderDao orderDao;
    private UserDao userDao;
    private AbstractEmailSender emailSender;

    @Autowired
    public ComplaintServiceImpl(ComplaintDao complaintDao, OrderDao orderDao, UserDao userDao,
                                @Qualifier("complaintSender") AbstractEmailSender emailSender) {
        this.complaintDao = complaintDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.emailSender = emailSender;
    }

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
        ModelMapper mapper = configureMapper();
        dto.setMessage(dto.getMessage().trim());
        Complaint complaint = mapper.map(dto, Complaint.class);
        Order order = orderDao.findById(dto.getOrderId());
        complaint.setOrder(order);
        User customer = userDao.findById(dto.getCustomerId());
        complaint.setCustomer(customer);
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
        if (complaint.getPmg() != null) {
            dto.setPmgId(complaint.getPmg().getId());
        }
        return complaint;
    }

    public List<Complaint> findByCustomerId(Long id) {
        return complaintDao.findAllByCustomerId(id);
    }

    public Complaint findById(Long id) {
        return complaintDao.findById(id);
    }

    private void sendEmail(Complaint complaint) {
        EmailMap emailMap = new EmailMap(EmailType.COMPLAINT);
        emailMap.put("complaint", complaint);
        try {
            emailSender.send(emailMap);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private ModelMapper configureMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new ComplaintMapper());
        return modelMapper;
    }
}
