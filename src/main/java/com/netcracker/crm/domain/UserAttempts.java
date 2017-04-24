package com.netcracker.crm.domain;

import java.util.Date;

/**
 * Created by Pasha on 22.04.2017.
 */
public class UserAttempts {

    private Integer id;

    private String userMail;

    private int attempts;

    private Date lastModified;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
