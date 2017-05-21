package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.service.entity.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by bpogo on 5/4/2017.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationDao organizationDao;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    @Override
    public Set<Organization> getAllOrganizations() {
        return organizationDao.getAll();
    }
}
