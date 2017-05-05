package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Organization;

import java.util.Set;

/**
 * Created by bpogo on 5/4/2017.
 */
public interface OrganizationService {
    Set<Organization> getAllOrganizations();
}
