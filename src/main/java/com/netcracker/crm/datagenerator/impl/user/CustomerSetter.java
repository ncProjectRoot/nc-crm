package com.netcracker.crm.datagenerator.impl.user;

import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Pasha on 05.05.2017.
 */
@Service
public class CustomerSetter extends AbstractUserSetter {
    private List<Organization> organizationList;
    private List<Address> addresses;
    @Override
    protected UserRole getRole() {
        return UserRole.ROLE_CUSTOMER;
    }

    @Override
    protected String getEmail(int counter) {
        return "customer" + counter + "@gmail.com";
    }

    @Override
    protected void setOrganization(User user) {
        user.setOrganization(organizationList.get(random.nextInt(organizationList.size())));
    }

    @Override
    protected void setAddress(User user) {
        user.setAddress(addresses.remove(random.nextInt(addresses.size())));
    }

    @Override
    protected void setContactPerson(User user, int counter) {
        user.setContactPerson(counter % 20 == 0);
    }

    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
