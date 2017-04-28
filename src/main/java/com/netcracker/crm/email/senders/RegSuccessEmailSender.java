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
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 14.04.2017
 */


@Service
public class RegSuccessEmailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(RegSuccessEmailSender.class);

    //Name of template html file for email letter
    private String regSuccessTempl;
    //Subject for email letter
    private String regSuccessSubj;

    @Qualifier("emailProps")
    @Autowired
    private Properties properties;

    @Autowired
    private JavaMailSenderImpl sender;

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


    public void send(EmailMap emailMap) throws MessagingException {
        checkEmailMap(emailMap);
        User user = getUser(emailMap);

        String template = replace(user, getTemplate(regSuccessTempl));
        EmailBuilder emailBuilder = new EmailBuilder();
        emailBuilder.setProperties(properties);
        log.debug("Start building email letter");
        emailBuilder.setSubject(regSuccessSubj);
        emailBuilder.setAddress(user.getEmail());
        emailBuilder.setContent(template);
        log.debug("Sending email");
        sender.send(emailBuilder.generateMessage());
    }

    private User getUser(EmailMap emailMap) {
        Object o = emailMap.get("user");
        if (o instanceof User){
            return (User) o;
        }else {
            throw new IncorrectEmailElementException("Expected by key 'user' in map will be user");
        }
    }



    private String replace(User user, String templ) {
        log.debug("Start replacing values in email template file");
        return templ.replaceAll("%email%", user.getEmail())
                .replaceAll("%name%", user.getFirstName())
                .replaceAll("%surname%", user.getLastName());
    }

    @Override
    protected void checkEmailMap(EmailMap emailMap) {
        if (EmailType.REGISTRATION != emailMap.getEmailType()){
            throw new IncorrectEmailElementException("Expected email type REGISTRATION but type " + emailMap.getEmailType());
        }
    }
}
