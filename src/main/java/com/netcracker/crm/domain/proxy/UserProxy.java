package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.dao.OrganizationDao;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.domain.model.User;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class UserProxy extends User {

    private Long addressId;
    private Long organizationId;

    private AddressDao addressDao;
    private OrganizationDao organizationDao;

    public UserProxy(AddressDao addressDao, OrganizationDao organizationDao) {
        this.addressDao = addressDao;
        this.organizationDao = organizationDao;
    }

    @Override
    public Address getAddress() {
        if (super.getAddress() == null && addressId != null) {
            super.setAddress(addressDao.findById(addressId));
        }
        return super.getAddress();
    }

    @Override
    public Organization getOrganization() {
        if (super.getOrganization() == null && organizationId != null) {
            super.setOrganization(organizationDao.findById(organizationId));
        }
        return super.getOrganization();
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
