package com.netcracker.crm.datagenerator.impl.user;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import org.springframework.stereotype.Service;

/**
 * Created by Pasha on 05.05.2017.
 */
@Service
public class PmgSetter extends AbstractUserSetter {
    @Override
    protected UserRole getRole() {
        return UserRole.ROLE_PMG;
    }

    @Override
    protected String getEmail(int counter) {
        return "pmg" + counter + "@gmail.com";
    }

    @Override
    protected void setOrganization(User user) {
//      TODO nothing
    }

    @Override
    protected void setContactPerson(User user, int counter) {
//        TODO nothing
    }
}
