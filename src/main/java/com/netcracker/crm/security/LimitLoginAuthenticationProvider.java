package com.netcracker.crm.security;

import com.netcracker.crm.dao.UserAttemptsDao;
import com.netcracker.crm.domain.UserAttempts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Pasha on 23.04.2017.
 */
@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
    //    timeout for user which has been locked
    private static final long TIME_OUT = 300_000L;
    private static final long MINUTES_IN_MILLISECONDS = 60_000;

    @Autowired
    private UserAttemptsDao userAttemptsDao;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAttempts userAttempts = userAttemptsDao.getUserAttempts(authentication.getName());
        try {
            if (checkTimeout(userAttempts)) {
                userAttemptsDao.lockUserAccount(authentication.getName(), false);
            }
            Authentication auth = super.authenticate(authentication);
            userAttemptsDao.resetFailAttempts(authentication.getName());
            return auth;
        } catch (BadCredentialsException e) {
            userAttemptsDao.updateFailAttempts(authentication.getName());
            throw e;
        } catch (LockedException e) {
            String error;
            if (userAttempts != null) {
                error = getInformMessage(authentication.getName(), userAttempts.getLastModified());
            } else {
                error = e.getMessage();
            }
            throw new LockedException(error);
        }

    }

    private boolean checkTimeout(UserAttempts userAttempts) {
        return userAttempts.getLastModified() != null &&
                userAttempts.getLastModified().getTime() + TIME_OUT < new Date().getTime();
    }

    private String getInformMessage(String userMail, Date lastModified) {
        long timeWait = new Date().getTime() - lastModified.getTime();
        long minutes = getMinutes(timeWait);
        long seconds = getSeconds(timeWait);
        return "User account is locked! <br><br>Username : "
                + userMail + "<br>Please wait : " + minutes + ":" + seconds;

    }

    private long getMinutes(long time) {
        return time / MINUTES_IN_MILLISECONDS;
    }

    private long getSeconds(long time) {
        return time % MINUTES_IN_MILLISECONDS;
    }
}
