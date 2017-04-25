package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Address;

/**
 * Created by bpogo on 4/24/2017.
 */
public interface AddressDao {
    Long create(Address address);

    Long update(Address address);

    Long delete(Long id);

    Address findByName(String name);
}
