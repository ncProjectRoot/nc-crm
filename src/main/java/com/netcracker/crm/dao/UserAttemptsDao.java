package com.netcracker.crm.dao;

import com.netcracker.crm.domain.UserAttempts;

/**
 * Created by Pasha on 22.04.2017.
 */
public interface UserAttemptsDao {

    void updateFailAttempts(String userMail);

    boolean resetFailAttempts(String userMail);

    boolean lockUserAccount(String userMail, boolean lock);

    UserAttempts getUserAttempts(String userMail);
}
