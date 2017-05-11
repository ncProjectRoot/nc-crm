package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 05.05.2017.
 */
@Service
public class OrganizationSetter extends AbstractSetter<Organization> {
    private int counter;
    @Autowired
    private OrganizationDao organizationDao;
    @Override
    public List<Organization> generate(int numbers) {
        List<Organization> organizations = new ArrayList<>();

        for (int i = 0; i < numbers; i++) {
            Organization organization = generateObject();
            organizationDao.create(organization);
            organizations.add(organization);
        }
        return organizations;
    }

    @Override
    public Organization generateObject() {
        Organization organization = new Organization();
        organization.setName("Organization" + counter++);
        return organization;
    }
}
