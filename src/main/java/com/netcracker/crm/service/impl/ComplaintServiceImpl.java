package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.request.ComplaintRowRequest;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.dto.mapper.ComplaintMapper;
import com.netcracker.crm.dto.row.ComplaintRowDto;
import com.netcracker.crm.service.ComplaintService;
import com.netcracker.crm.service.email.AbstractEmailSender;
import com.netcracker.crm.service.email.EmailParam;
import com.netcracker.crm.service.email.EmailParamKeys;
import com.netcracker.crm.service.email.EmailType;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private HistoryDao historyDao;
    private AbstractEmailSender emailSender;

    @Autowired
    public ComplaintServiceImpl(ComplaintDao complaintDao, OrderDao orderDao, UserDao userDao,
                                @Qualifier("complaintSender") AbstractEmailSender emailSender,
                                HistoryDao historyDao) {
        this.complaintDao = complaintDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.emailSender = emailSender;
        this.historyDao = historyDao;
    }

    @Transactional
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

    public List<Complaint> findByCustomerId(Long id) {
        return complaintDao.findAllByCustomerId(id);
    }

    public Complaint findById(Long id) {
        return complaintDao.findById(id);
    }

    @Transactional
    @Override
    public Map<String, Object> getComplaintRow(ComplaintRowRequest complaintRowRequest) throws IOException {
        Map<String, Object> response = new HashMap<>();
        Long length = complaintDao.getComplaintRowsCount(complaintRowRequest);
        response.put("length", length);
        List<Complaint> complaints = complaintDao.findComplaintRows(complaintRowRequest);
        List<ComplaintRowDto> dtos = new ArrayList<>();
        for (Complaint complaint : complaints) {
            dtos.add(convertToRowDto(complaint));
        }
        response.put("rows", dtos);
        return response;
    }

    @Transactional
    @Override
    public List<String> getNames(String likeTitle) {
        return complaintDao.findProductsTitleLikeTitle(likeTitle);
    }

    @Transactional
    @Override
    public List<String> getNamesByPmgId(String likeTitle, Long pmgId) {
        return complaintDao.findProductsTitleByPmgId(likeTitle, pmgId);
    }

    @Transactional
    @Override
    public List<String> getNamesByCustId(String likeTitle, Long custId) {
        return complaintDao.findProductsTitleByCustId(likeTitle, custId);
    }

    @Transactional
    @Override
    public boolean acceptComplaint(Long complaintId, Long pmgId) {
        Complaint complaint = complaintDao.findById(complaintId);

        if(complaint.getPmg()!=null){
            return false;
        }
        User pmg = new User();
        pmg.setId(pmgId);
        complaint.setPmg(pmg);
        complaint.setStatus(ComplaintStatus.SOLVING);
        complaintDao.update(complaint);
        History history = new History();
        history.setComplaint(complaint);
        history.setDateChangeStatus(LocalDateTime.now());
        history.setDescChangeStatus("Pmg with id " + pmgId + " accepted complaint");
        history.setOldStatus(ComplaintStatus.OPEN);
        historyDao.create(history);
        return true;
    }

    @Transactional
    @Override
    public boolean closeComplaint(Long complaintId, Long pmgId) {
        Complaint complaint = complaintDao.findById(complaintId);
        if(complaint.getPmg()==null||!complaint.getPmg().getId().equals(pmgId)){
            return false;
        }
        complaint.setStatus(ComplaintStatus.CLOSED);
        complaintDao.update(complaint);
        History history = new History();
        history.setComplaint(complaint);
        history.setDateChangeStatus(LocalDateTime.now());
        history.setDescChangeStatus("Pmg with id " + complaint.getPmg().getId() + " solved complaint");
        history.setOldStatus(ComplaintStatus.SOLVING);
        historyDao.create(history);
        return true;
    }

    private ComplaintRowDto convertToRowDto(Complaint complaint) {
        ComplaintRowDto complaintRowDto = new ComplaintRowDto();
        complaintRowDto.setId(complaint.getId());
        complaintRowDto.setTitle(complaint.getTitle());
        complaintRowDto.setMessage(complaint.getMessage());
        complaintRowDto.setStatus(complaint.getStatus().getName());
        complaintRowDto.setCustomer(complaint.getCustomer().getId());
        complaintRowDto.setOrder(complaint.getOrder().getId());
        complaintRowDto.setOrderStatus(complaint.getOrder().getStatus().getName());
        complaintRowDto.setProductTitle(complaint.getOrder().getProduct().getTitle());
        complaintRowDto.setProductStatus(complaint.getOrder().getProduct().getStatus().getName());
        complaintRowDto.setDate(complaint.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm")));
        if (complaint.getPmg() != null) {
            complaintRowDto.setPmg(complaint.getPmg().getId());
        }
        return complaintRowDto;
    }

    private void sendEmail(Complaint complaint) {
        EmailParam emailMap = new EmailParam(EmailType.COMPLAINT);
        emailMap.put(EmailParamKeys.COMPLAINT, complaint);
        try {
            emailSender.send(emailMap);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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

    private ModelMapper configureMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new ComplaintMapper());
        return modelMapper;
    }
}
