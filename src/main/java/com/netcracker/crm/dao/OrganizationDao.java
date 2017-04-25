package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Organization;

/**
 * Created by bpogo on 4/24/2017.
 */
public interface OrganizationDao {
    Long create(Organization org);

    Long update(Organization org);

    Long delete(Long id);

    Organization findByName(String name);
}
