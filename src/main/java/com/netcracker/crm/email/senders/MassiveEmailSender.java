package com.netcracker.crm.email.senders;

import com.netcracker.crm.email.builder.EmailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Pasha on 14.04.2017.
 */
@Service
@Scope("prototype")
public class MassiveEmailSender extends AbstractEmailSender {


    private String informationAll;
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private EmailBuilder emailBuilder;

    public void sendMails(String[] to, String subject, String information) throws MessagingException {
        String bodyText = replace(getTemplate(informationAll), information);
        send(createMessage(to, subject, bodyText));
    }




    private MimeMessage createMessage(String[] to, String subject, String bodyText) throws MessagingException {
        emailBuilder.setAllAddress(to);
        emailBuilder.setSubject(subject);
        emailBuilder.setContent(bodyText);
        return emailBuilder.generateMessage();
    }

    private void send(MimeMessage message) throws MessagingException {
        mailSender.send(message);
    }

    private String replace(String html, String information){
        return html.replace("%information%", information);
    }

    public String getInformationAll() {
        return informationAll;
    }

    public void setInformationAll(String informationAll) {
        this.informationAll = informationAll;
    }
}
