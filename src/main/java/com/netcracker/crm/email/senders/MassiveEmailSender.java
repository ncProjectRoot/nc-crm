package com.netcracker.crm.email.senders;

import com.netcracker.crm.email.builder.EmailBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Pasha on 14.04.2017.
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MassiveEmailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(MassiveEmailSender.class);

    private String informationAll;

    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private EmailBuilder emailBuilder;

    public void send(String[] receivers, String subject, String body) throws MessagingException {
        if (receivers == null || receivers.length == 0) {
            log.error("Receivers can't be null or have zero length");
            throw new IllegalStateException("receivers is null or no recipient");
        } else if (subject == null) {
            log.error("Subject can't be null");
            throw new IllegalStateException("subject is null");
        } else if (body == null) {
            log.error("Body can't be null");
            throw new IllegalStateException("body is null");
        } else {
            String bodyText = replace(getTemplate(informationAll), body);
            sendMails(createMessage(receivers, subject, bodyText));
        }
    }

    private MimeMessage createMessage(String[] to, String subject, String bodyText) throws MessagingException {
        log.debug("Start building email letter");
        emailBuilder.setAllAddress(to);
        emailBuilder.setSubject(subject);
        emailBuilder.setContent(bodyText);
        return emailBuilder.generateMessage();
    }

    private void sendMails(MimeMessage message) throws MessagingException {
        log.debug("Sending emails");
        mailSender.send(message);
    }

    String replace(String html, String body) {
        log.debug("Start replacing values in email template file");
        return html.replaceAll("%information%", body);
    }

    public String getInformationAll() {
        return informationAll;
    }

    public void setInformationAll(String informationAll) {
        this.informationAll = informationAll;
    }

}
