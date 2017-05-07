package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.ComplaintRowRequest;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.entity.ComplaintService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.ComplaintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.controller.message.MessageHeader.ERROR_MESSAGE;
import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_SERVER_ERROR;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_COMPLAINT_CREATED;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 01.05.2017
 */

@RestController
public class ComplaintRestController {

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private ResponseGenerator<Complaint> generator;
    @Autowired
    private BindingResultHandler bindingResultHandler;
    @Autowired
    private ComplaintValidator complaintValidator;

    @RequestMapping(value = "/customer/createComplaint", method = RequestMethod.POST)
    public ResponseEntity<?> createComplaint(@Valid ComplaintDto complaintDto, BindingResult bindingResult, Authentication authentication) {
        complaintValidator.validate(complaintDto, bindingResult);
        if (bindingResult.hasErrors()){
            return bindingResultHandler.handle(bindingResult);
        }
        Object principal = authentication.getPrincipal();
        User user = (UserDetailsImpl) principal;
        complaintDto.setCustomerId(user.getId());

        Complaint complaint = complaintService.persist(complaintDto);
        if(complaint.getId() > 0){
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_COMPLAINT_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/pmg/load/complaints")
    public Map<String, Object> complaints(ComplaintRowRequest complaintRowRequest) throws IOException {
        return complaintService.getComplaintRow(complaintRowRequest);
    }

    @GetMapping("/pmg/load/complaintsNames")
    public List<String> complaintsNames(String likeTitle) {
        return complaintService.getNames(likeTitle);
    }

    @GetMapping("/pmg/load/ownComplaints")
    public Map<String, Object> ownComplaints(ComplaintRowRequest complaintRowRequest, Authentication authentication) throws IOException {
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

    @GetMapping("/pmg/load/ownComplaintsNames")
    public List<String> ownComplaintsNames(String likeTitle, Authentication authentication) {
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


}
