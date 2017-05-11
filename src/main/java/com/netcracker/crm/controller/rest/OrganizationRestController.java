package com.netcracker.crm.controller.rest;

import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.exception.RegistrationException;
import com.netcracker.crm.service.entity.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by bpogo on 5/4/2017.
 */
@RestController
@RequestMapping("/organizations")
public class OrganizationRestController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationRestController.class);

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<Set<Organization>> registerUser() throws RegistrationException {
        Set<Organization> organizations = organizationService.getAllOrganizations();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }
}
