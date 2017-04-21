package com.netcracker.crm.email.senders;

import com.netcracker.crm.email.builder.EmailBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(MassiveEmailSender.class);

    private String informationAll;

    private String[] receivers;
    private String subject;
    private String body;

    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private EmailBuilder emailBuilder;

    public void send() throws MessagingException {
        if (receivers == null||receivers.length==0) {
            log.error("You must set receivers before sending");
            throw new IllegalStateException("receivers is null or no recipient");
        } else if (subject == null) {
            log.error("You must set subject before sending");
            throw new IllegalStateException("subject is null");
        } else if (body == null) {
            log.error("You must set body before sending");
            throw new IllegalStateException("body is null");
        } else {
            String bodyText = replace(getTemplate(informationAll));
            sendMails(createMessage(receivers, subject, bodyText));
        }
    }

    private MimeMessage createMessage(String[] to, String subject, String bodyText) throws MessagingException {
        log.info("Start building email letter");
        emailBuilder.setAllAddress(to);
        emailBuilder.setSubject(subject);
        emailBuilder.setContent(bodyText);
        return emailBuilder.generateMessage();
    }

    private void sendMails(MimeMessage message) throws MessagingException {
        log.info("Sending emails");
        mailSender.send(message);
    }

    String replace(String html) {
        log.info("Start replacing values in email template file");
        return html.replace("%information%", body);
    }

    public String getInformationAll() {
        return informationAll;
    }

    public void setInformationAll(String informationAll) {
        this.informationAll = informationAll;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
