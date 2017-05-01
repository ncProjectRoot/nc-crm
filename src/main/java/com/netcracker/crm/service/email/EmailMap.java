package com.netcracker.crm.service.email;

import com.netcracker.crm.exception.IncorrectEmailElementException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pasha on 26.04.2017.
 */
public class EmailMap {
    private Map<EmailMapKeys, Object> emailMap;
    private EmailType emailType;


    public EmailMap(EmailType emailType) {
        if (emailType == null){
            throw new IncorrectEmailElementException("Email type == null");
        }
        this.emailType = emailType;
        emailMap = new HashMap<>();
    }


    public void put(EmailMapKeys key, Object value){
        emailMap.put(key, value);
    }


    public void remove(EmailMapKeys key){
        emailMap.remove(key);
    }

    public Object get(EmailMapKeys key){
        return emailMap.get(key);
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public Map<EmailMapKeys, Object> getEmailMap() {
        return emailMap;
    }
}
