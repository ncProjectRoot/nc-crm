package com.netcracker.crm.email.senders;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.email.builder.EmailBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 14.04.2017
 */


@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RegSuccessEmailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(RegSuccessEmailSender.class);

    //Name of template html file for email letter
    private String regSuccessTempl;
    //Subject for email letter
    private String regSuccessSubj;

    @Autowired
    private EmailBuilder builder;

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


    public void send(User user) throws MessagingException {
        if (user == null) {
            log.error("User can't be null");
            throw new IllegalStateException("user is null");
        } else {
            String template = getTemplate(regSuccessTempl);
            template = replace(user, template);
            log.debug("Start building email letter");
            builder.setSubject(regSuccessSubj);
            builder.setAddress(user.getEmail());
            builder.setContent(template);
            log.debug("Sending email");
            sender.send(builder.generateMessage());
        }
    }

    String replace(User user, String templ) {
        log.debug("Start replacing values in email template file");
        return templ.replaceAll("%email%", user.getEmail())
                .replaceAll("%name%", user.getFirstName())
                .replaceAll("%surname%", user.getLastName());
    }
}
