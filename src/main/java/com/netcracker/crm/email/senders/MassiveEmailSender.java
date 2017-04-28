package com.netcracker.crm.email.senders;

import com.netcracker.crm.email.builder.EmailBuilder;
import com.netcracker.crm.exception.IncorrectEmailElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Pasha on 14.04.2017.
 */
@Service
public class MassiveEmailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(MassiveEmailSender.class);

    private String informationAll;

    @Autowired
    private JavaMailSenderImpl mailSender;
    @Qualifier("emailProps")
    @Autowired
    private Properties properties;

    public void send(EmailMap emailMap) throws MessagingException {
        checkEmailMap(emailMap);
        String[] receivers = getReceivers(emailMap);
        String subject = getSubject(emailMap);
        String body = getBody(emailMap);
        String bodyText = replace(getTemplate(informationAll), body);
        sendMails(createMessage(receivers, subject, bodyText));
    }

    private String[] getReceivers(EmailMap emailMap){
        String[] receivers = (String[]) emailMap.get("receivers");
        if (receivers == null || receivers.length == 0){
            log.error("Receivers can't be null or have zero length");
            throw new IllegalStateException("receivers is null or no recipient");
        }
        return receivers;
    }

    private String getSubject(EmailMap emailMap){
        String subject = (String) emailMap.get("subject");
        if (subject == null){
            log.error("Subject can't be null");
            throw new IllegalStateException("subject is null");
        }
        return subject;
    }

    private String getBody(EmailMap emailMap){
        String body = (String) emailMap.get("subject");
        if (body == null){
            log.error("Body can't be null");
            throw new IllegalStateException("body is null");
        }
        return body;
    }


    private MimeMessage createMessage(String[] to, String subject, String bodyText) throws MessagingException {
        EmailBuilder emailBuilder = new EmailBuilder();
        emailBuilder.setProperties(properties);
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

    @Override
    protected void checkEmailMap(EmailMap emailMap) {
        if (EmailType.MASSIVE != emailMap.getEmailType()){
            throw new IncorrectEmailElementException("Expected email type MASSIVE but type " + emailMap.getEmailType());
        }
    }
}
