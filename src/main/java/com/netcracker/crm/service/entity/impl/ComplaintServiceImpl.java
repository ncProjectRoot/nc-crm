package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.request.ComplaintRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.dto.GraphDto;
import com.netcracker.crm.dto.mapper.ComplaintMapper;
import com.netcracker.crm.dto.row.ComplaintRowDto;
import com.netcracker.crm.listener.event.ChangeStatusComplaintEvent;
import com.netcracker.crm.listener.event.CreateComplaintEvent;
import com.netcracker.crm.service.entity.ComplaintService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private ApplicationEventPublisher publisher;
    private ComplaintDao complaintDao;
    private OrderDao orderDao;
    private UserDao userDao;
    private HistoryDao historyDao;

    @Autowired
    public ComplaintServiceImpl(ComplaintDao complaintDao, OrderDao orderDao, UserDao userDao, HistoryDao historyDao,
                                ApplicationEventPublisher publisher) {
        this.complaintDao = complaintDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.historyDao = historyDao;
        this.publisher = publisher;
    }

    @Transactional
    public Complaint persist(ComplaintDto dto) {
        Complaint complaint = convertToModel(dto);
        complaint.setDate(LocalDateTime.now());
        complaint.setStatus(ComplaintStatus.OPEN);
        Long id = complaintDao.create(complaint);
        complaint.setId(id);
        publisher.publishEvent(new CreateComplaintEvent(this, complaint));
        return complaint;
    }

    @Transactional
    public List<Complaint> findByTitle(String title) {
        return complaintDao.findByTitle(title);
    }

    @Transactional
    public List<Complaint> findByDate(LocalDate date) {
        return complaintDao.findAllByDate(date);
    }

    @Transactional
    public List<Complaint> findByCustomerId(Long id) {
        return complaintDao.findAllByCustomerId(id);
    }

    @Transactional
    public Complaint findById(Long id) {
        return complaintDao.findById(id);
    }

    @Transactional
    private List<AutocompleteDto> getTitlesByPmg(String likeTitle, User pmg) {
        return convertToAutocompleteDto(complaintDao.findComplaintsTitleByPmgId(likeTitle, pmg.getId()));
    }

    @Transactional
    private List<AutocompleteDto> getAllTitles(String likeTitle) {
        return convertToAutocompleteDto(complaintDao.findComplaintsTitleLikeTitle(likeTitle));
    }

    @Transactional
    private List<AutocompleteDto> getTitlesForContactPerson(String likeTitle, Long custId) {
        return convertToAutocompleteDto(complaintDao.findComplaintsTitleForContactPerson(likeTitle, custId));
    }

    @Transactional
    private List<AutocompleteDto> getTitlesForNotContactPerson(String likeTitle, Long custId) {
        return convertToAutocompleteDto(complaintDao.findComplaintsTitleByCustId(likeTitle, custId));
    }

    @Transactional
    @Override
    public boolean changeStatusComplaint(Long complaintId, String type, User pmg) {
        if ("ACCEPT".equals(type)) {
            return acceptComplaint(complaintId, pmg);
        } else if ("CLOSE".equals(type)) {
            return closeComplaint(complaintId, pmg);
        }
        return false;
    }

    @Transactional
    @Override
    public Map<String, Object> getComplaintRow(ComplaintRowRequest complaintRowRequest, User user, boolean individual) {
        UserRole role = user.getUserRole();
        if (role.equals(UserRole.ROLE_CUSTOMER)) {
            complaintRowRequest.setCustId(user.getId());
            if (user.isContactPerson()) {
                complaintRowRequest.setContactPerson(true);
            }
        } else if ((role.equals(UserRole.ROLE_PMG) || role.equals(UserRole.ROLE_ADMIN)) && (individual)) {
            complaintRowRequest.setPmgId(user.getId());
        }
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
    public List<AutocompleteDto> getAutocompleteDto(String pattern, User user, boolean individual) {
        UserRole role = user.getUserRole();
        if (role.equals(UserRole.ROLE_PMG) || role.equals(UserRole.ROLE_ADMIN)) {
            if (individual) {
                return getTitlesByPmg(pattern, user) ;
            } else {
                return getAllTitles(pattern);
            }
        } else if (role.equals(UserRole.ROLE_CUSTOMER)) {
            if (user.isContactPerson()) {
                return getTitlesForContactPerson(pattern, user.getId());
            } else {
                return getTitlesForNotContactPerson(pattern, user.getId());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public GraphDto getStatisticalGraph(GraphDto graphDto) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(graphDto.getFromDate(), dtf);
        LocalDate toDate = LocalDate.parse(graphDto.getToDate(), dtf);
        return historyDao.findComplaintHistoryBetweenDateChangeByProductIds(fromDate, toDate, graphDto);
    }

    private List<AutocompleteDto> convertToAutocompleteDto(List<String> complaints) {
        List<AutocompleteDto> autocompleteDtos = new ArrayList<>();
        for (String title : complaints) {
            AutocompleteDto autocompleteDto = new AutocompleteDto();
            autocompleteDto.setValue(title);
            autocompleteDtos.add(autocompleteDto);
        }
        return autocompleteDtos;
    }

    @Transactional
    private boolean acceptComplaint(Long complaintId, User user) {
        Complaint complaint = complaintDao.findById(complaintId);

        if (complaint.getPmg() != null) {
            return false;
        }
        complaint.setPmg(user);
        publisher.publishEvent(new ChangeStatusComplaintEvent(this, complaint));
        return true;
    }

    @Transactional
    private boolean closeComplaint(Long complaintId, User user) {
        Boolean isRoleAdmin = user.getUserRole().equals(UserRole.ROLE_ADMIN);
        Complaint complaint = complaintDao.findById(complaintId);
        if (complaint.getPmg() == null || (!complaint.getPmg().getId().equals(user.getId())) && (!isRoleAdmin)) {
            return false;
        }
        if (isRoleAdmin) {
            complaint.setPmg(user);
        }
        publisher.publishEvent(new ChangeStatusComplaintEvent(this, complaint));
        return true;
    }

    @Transactional
    @Override
    public boolean checkAccessToComplaint(User customer, Long complaintId) {
        UserRole role = customer.getUserRole();
        if (role.equals(UserRole.ROLE_ADMIN) || role.equals(UserRole.ROLE_PMG)) {
            return true;
        } else if (role.equals(UserRole.ROLE_CSR)) {
            return false;
        } else if (role.equals(UserRole.ROLE_CUSTOMER)) {
            Long count = null;
            if (customer.isContactPerson()) {
                count = complaintDao.checkOwnershipOfContactPerson(complaintId, customer.getId());
            } else {
                count = complaintDao.checkOwnershipOfCustomer(complaintId, customer.getId());
            }
            return count > 0;
        }
        return false;
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
