package com.netcracker.crm.email.senders;

import com.netcracker.crm.exception.IncorrectEmailElementException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pasha on 26.04.2017.
 */
public class EmailMap {
    private Map<String, Object> emailMap;
    private EmailType emailType;


    public EmailMap(EmailType emailType) {
        if (emailType == null){
            throw new IncorrectEmailElementException("Email type == null");
        }
        this.emailType = emailType;
        emailMap = new LinkedHashMap<>();
    }


    public void put(String valueName, Object value){
        emailMap.put(valueName, value);
    }


    public void remove(String valueName){
        emailMap.remove(valueName);
    }

    public Object get(String valueName){
        return emailMap.get(valueName);
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public Map<String, Object> getEmailMap() {
        return emailMap;
    }
}
