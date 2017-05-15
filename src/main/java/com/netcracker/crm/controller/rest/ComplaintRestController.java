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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping(value = "/complaints")
public class ComplaintRestController {


    private ComplaintService complaintService;
    private ResponseGenerator<Complaint> generator;
    private BindingResultHandler bindingResultHandler;
    private ComplaintValidator complaintValidator;

    @Autowired
    public ComplaintRestController(ComplaintService complaintService, ResponseGenerator<Complaint> generator,
                                   BindingResultHandler bindingResultHandler, ComplaintValidator complaintValidator) {
        this.complaintService = complaintService;
        this.generator = generator;
        this.bindingResultHandler = bindingResultHandler;
        this.complaintValidator = complaintValidator;
    }

    @PostMapping
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

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN', 'ROLE_PMG')")
    public ResponseEntity<Map<String, Object>> complaints(ComplaintRowRequest complaintRowRequest, Authentication authentication,
                                                          @RequestParam(required = false) boolean individual) {
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        }
        return new ResponseEntity<>(complaintService.getComplaintRow(complaintRowRequest, user, individual), HttpStatus.OK);
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN', 'ROLE_PMG')")
    public ResponseEntity<List<String>> complaintsTitles(String likeTitle, Authentication authentication,
                                                         @RequestParam(required = false) boolean individual) {
        Object principal = authentication.getPrincipal();
        User user  = (UserDetailsImpl) principal;
        return new ResponseEntity<>(complaintService.getTitles(likeTitle, user, individual), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_PMG', 'ROLE_ADMIN')")
    public ResponseEntity<Boolean> acceptOrCloseComplaint(Authentication authentication,
                                                   @RequestParam(value = "type") String type,
                                                   @PathVariable Long id) {
        Object principal = authentication.getPrincipal();
        User pmg = (UserDetailsImpl) principal;
        return new ResponseEntity<>(complaintService.changeStatusComplaint(id, type, pmg), HttpStatus.OK);
    }
}
