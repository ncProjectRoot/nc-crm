package com.netcracker.crm.email.senders;

import com.netcracker.crm.email.builder.EmailBuilder;
import com.netcracker.crm.email.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 14.04.2017
 */


@Service
public class RegSuccessEmailSender extends AbstractEmailSender {

    //Name of template html file for email letter
    private String regSuccessTempl;
    //Subject for email letter
    private String regSuccessSubj;

    @Autowired
    private EmailBuilder builder;

    @Autowired
    private JavaMailSenderImpl sender;

    private ReentrantLock lock = new ReentrantLock();

    public RegSuccessEmailSender() {
    }

    public String getRegSuccessTempl() {
        return regSuccessTempl;
    }

    public void setRegSuccessTempl(String regSuccessTempl) {
        this.regSuccessTempl = regSuccessTempl;
    }

    public String getRegSuccessSubj() {
        return regSuccessSubj;
    }

    public void setRegSuccessSubj(String regSuccessSubj) {
        this.regSuccessSubj = regSuccessSubj;
    }

    public void send(User user) throws MessagingException {
        lock.lock();
        String template = getTemplate(regSuccessTempl);
        template = replaceFields(template, user);
        builder.setSubject(regSuccessSubj);
        builder.setAddress(user.getEmail());
        builder.setContent(template);
        sender.send(builder.generateMessage());
        lock.unlock();
    }

    private String replaceFields(String templ, User user) {
        return templ.replaceAll("%username%", user.getLogin())
                .replaceAll("%email%", user.getEmail())
                .replaceAll("%name%", user.getName())
                .replaceAll("%surname%", user.getSurname());
    }
}
