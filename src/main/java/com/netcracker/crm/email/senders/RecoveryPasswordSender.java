package com.netcracker.crm.email.senders;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.email.builder.EmailBuilder;
import com.netcracker.crm.exception.IncorrectEmailElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Properties;

/**
 * Created by Pasha on 26.04.2017.
 */
@Service
public class RecoveryPasswordSender extends AbstractEmailSender {
    private static final Logger log = LoggerFactory.getLogger(RecoveryPasswordSender.class);
    @Qualifier("emailProps")
    @Autowired
    private Properties properties;

    @Autowired
    private JavaMailSenderImpl mailSender;


    private String recoveryTemplate;
    private String recoverySubject;

    @Override
    public void send(EmailMap emailMap) throws MessagingException {
        checkEmailMap(emailMap);
        User user = getUser(emailMap);
        String password = getPassword(emailMap);
        prepareAndSendMail(user, password);
    }

    private void prepareAndSendMail(User user, String password) throws MessagingException {
        String template = replace(getTemplate(recoveryTemplate), user, password);
        buildAndSend(user, recoverySubject, template);
    }

    private void buildAndSend(User user, String subject, String body) throws MessagingException {
        EmailBuilder emailBuilder = new EmailBuilder();
        emailBuilder.setProperties(properties);
        log.debug("Start building email letter");
        emailBuilder.setContent(body);
        emailBuilder.setAddress(user.getEmail());
        emailBuilder.setSubject(subject);
        log.debug("Sending email");
        mailSender.send(emailBuilder.generateMessage());
    }

    private String replace(String html, User user, String password) {
        log.debug("Start replacing values in email template file");
        return html.replaceAll("%name%", user.getFirstName())
                .replaceAll("%surname%", user.getMiddleName())
                .replaceAll("%password%", password);
    }

    private String getPassword(EmailMap emailMap){
        String password = (String) emailMap.get("password");
        if (password == null){
            log.error("Password can't be null");
            throw new IllegalStateException("password is null");
        }
        return password;
    }

    private User getUser(EmailMap emailMap) {
        Object o = emailMap.get("user");
        if (o instanceof User){
            return (User) o;
        }else {
            log.error("Expected by key 'user' in map will be user");
            throw new IncorrectEmailElementException("Expected by key 'user' in map will be user");
        }
    }

    @Override
    protected void checkEmailMap(EmailMap emailMap) {
        if (EmailType.RECOVERY_PASSWORD != emailMap.getEmailType()){
            throw new IncorrectEmailElementException("Expected email type RECOVERY_PASSWORD but type " + emailMap.getEmailType());
        }
    }

    public String getRecoveryTemplate() {
        return recoveryTemplate;
    }

    public void setRecoveryTemplate(String recoveryTemplate) {
        this.recoveryTemplate = recoveryTemplate;
    }

    public String getRecoverySubject() {
        return recoverySubject;
    }

    public void setRecoverySubject(String recoverySubject) {
        this.recoverySubject = recoverySubject;
    }
}
