package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.domain.request.ComplaintRowRequest;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.entity.ComplaintService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.ComplaintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/complaints")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createComplaint(@Valid ComplaintDto complaintDto, BindingResult bindingResult, Authentication authentication) {
        complaintValidator.validate(complaintDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResultHandler.handle(bindingResult);
        }
        Object principal = authentication.getPrincipal();
        User user = (UserDetailsImpl) principal;
        complaintDto.setCustomerId(user.getId());

        Complaint complaint = complaintService.persist(complaintDto);
        if (complaint.getId() > 0) {
            return generator.getHttpResponse(complaint.getId(), SUCCESS_MESSAGE, SUCCESS_COMPLAINT_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/complaints")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN', 'ROLE_PMG')")
    public ResponseEntity<Map<String, Object>> complaints(ComplaintRowRequest complaintRowRequest, Authentication authentication,
                                                          @RequestParam(required = false) Long userId) throws IOException {
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        }

        if (userId != null) {
            UserRole role = user.getUserRole();
            if (role.equals(UserRole.ROLE_PMG)) {
                complaintRowRequest.setPmgId(user.getId());
            } else if (role.equals(UserRole.ROLE_CUSTOMER)) {
                complaintRowRequest.setCustId(user.getId());
                if (user.isContactPerson()) {
                    complaintRowRequest.setContactPerson(true);
                }
            }
        }
        return new ResponseEntity<>(complaintService.getComplaintRow(complaintRowRequest), HttpStatus.OK);
    }

    @GetMapping("/complaints/titles")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN', 'ROLE_PMG')")
    public ResponseEntity<List<String>> complaintsTitles(String likeTitle, Authentication authentication,
                                                         @RequestParam(required = false) Long userId) {
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        }
        if (userId != null && user.getUserRole().equals(UserRole.ROLE_PMG)) {
            new ResponseEntity<>(complaintService.getTitlesByPmg(likeTitle, user), HttpStatus.OK);
        }
        return new ResponseEntity<>(complaintService.getTitles(likeTitle, user), HttpStatus.OK);
    }

    @PutMapping("/complaints/{id}")
    @PreAuthorize("hasRole('ROLE_PMG')")
    public ResponseEntity<Boolean> acceptComplaint(Map<String, Object> model, Authentication authentication,
                                                   @RequestParam(value = "type") String type,
                                                   @PathVariable Long id) {
        Object principal = authentication.getPrincipal();
        User pmg = null;
        if (principal instanceof UserDetailsImpl) {
            pmg = (UserDetailsImpl) principal;
        }
        if ("ACCEPT".equals(type)) {
            return new ResponseEntity<>(complaintService.acceptComplaint(id, pmg.getId()), HttpStatus.OK);
        } else if ("CLOSE".equals(type)) {
            return new ResponseEntity<>(complaintService.closeComplaint(id, pmg.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }
}
