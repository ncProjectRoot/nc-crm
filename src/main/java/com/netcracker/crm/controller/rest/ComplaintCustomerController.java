package com.netcracker.crm.controller.rest;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 01.05.2017
 */

@RestController
public class ComplaintCustomerController {

    @Autowired
    private ComplaintService complaintService;

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


}
