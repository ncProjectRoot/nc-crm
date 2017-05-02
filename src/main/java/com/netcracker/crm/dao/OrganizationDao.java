package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Organization;

/**
 * Created by bpogo on 4/24/2017.
 */
public interface OrganizationDao extends CrudDao<Organization>{

    Organization findByName(String name);
}
