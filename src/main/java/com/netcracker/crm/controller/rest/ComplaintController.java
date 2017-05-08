package com.netcracker.crm.controller.rest;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.ComplaintRowRequest;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 06.05.2017
 */

@RestController
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/pmg/load/complaints")
    public Map<String, Object> complaints(ComplaintRowRequest complaintRowRequest) throws IOException {
        return complaintService.getComplaintRow(complaintRowRequest);
    }

    @GetMapping("/pmg/load/complaintsNames")
    public List<String> complaintsNames(String likeTitle) {
        return complaintService.getNames(likeTitle);
    }

    @GetMapping("/pmg/load/pmgComplaints")
    public Map<String, Object> pmgComplaints(ComplaintRowRequest complaintRowRequest, Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        User pmg = null;
        if (principal instanceof UserDetailsImpl) {
            pmg = (UserDetailsImpl) principal;
        } else {
            //!production
            pmg = new User();
            pmg.setId(5002L);
        }
        complaintRowRequest.setPmgId(pmg.getId());
        return complaintService.getComplaintRow(complaintRowRequest);
    }

    @GetMapping("/pmg/load/pmgComplaintsNames")
    public List<String> pmgComplaintsNames(String likeTitle, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User pmg = null;
        if (principal instanceof UserDetailsImpl) {
            pmg = (UserDetailsImpl) principal;
        } else {
            //!production
            pmg = new User();
            pmg.setId(5002L);
        }
        return complaintService.getNamesByPmgId(likeTitle, pmg.getId());
    }

    @RequestMapping(value = "/customer/createComplaint", method = RequestMethod.POST)
    public Complaint createComplaint(ComplaintDto complaintDto, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
            complaintDto.setCustomerId(user.getId());
        } else {
            //!production
            complaintDto.setCustomerId(1L);
        }
        Complaint complaint = complaintService.persist(complaintDto);
        return complaint;
    }

    @GetMapping("/customer/load/complaints")
    public Map<String, Object> customerComplaints(ComplaintRowRequest complaintRowRequest, Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        User customer = null;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
        } else {
            //!production
            customer = new User();
            customer.setId(3L);
        }
        complaintRowRequest.setCustId(customer.getId());
        if (customer.isContactPerson()) {
            complaintRowRequest.setContactPerson(true);
        }
        return complaintService.getComplaintRow(complaintRowRequest);
    }

    @GetMapping("/customer/load/complaintsNames")
    public List<String> customerComplaintsNames(String likeTitle, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User customer = null;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
        } else {
            //!production
            customer = new User();
            customer.setId(3L);
        }
        return complaintService.getNamesByCustomer(likeTitle, customer);
    }

    @PostMapping("/pmg/acceptComplaint")
    public boolean acceptComplaint(Map<String, Object> model, Long complaintId, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User pmg = null;
        if (principal instanceof UserDetailsImpl) {
            pmg = (UserDetailsImpl) principal;
        } else {
            //!production
            pmg = new User();
            pmg.setId(5002L);
        }
        return complaintService.acceptComplaint(complaintId, pmg.getId());
    }

    @PostMapping("/pmg/closeComplaint")
    public boolean closeComplaint(Map<String, Object> model, Long complaintId, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User pmg = null;
        if (principal instanceof UserDetailsImpl) {
            pmg = (UserDetailsImpl) principal;
        } else {
            //!production
            pmg = new User();
            pmg.setId(5002L);
        }
        return complaintService.closeComplaint(complaintId, pmg.getId());
    }

}
