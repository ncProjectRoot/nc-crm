package com.netcracker.crm.email.senders;


import com.netcracker.crm.email.builder.EmailBuilder;
import com.netcracker.crm.email.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 14.04.2017
 */

@Service
@Scope("prototype")
public class ChangeStatusServiceEmailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(ChangeStatusServiceEmailSender.class);

    //Name of template html file for email letter
    private String changeStatusServiceTempl ;
    //Subject for email letter
    private String changeStatusServiceSubj ;

    private User user;
    private ServiceEntity serviceEntity;

    @Autowired
    private EmailBuilder builder;

    @Autowired
    private JavaMailSenderImpl sender;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceEntity getServiceEntity() {
        return serviceEntity;
    }

    public void setServiceEntity(ServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    public String getChangeStatusServiceTempl() {
        return changeStatusServiceTempl;
    }

    public void setChangeStatusServiceTempl(String changeStatusServiceTempl) {
        this.changeStatusServiceTempl = changeStatusServiceTempl;
    }

    public String getChangeStatusServiceSubj() {
        return changeStatusServiceSubj;
    }

    public void setChangeStatusServiceSubj(String changeStatusServiceSubj) {
        this.changeStatusServiceSubj = changeStatusServiceSubj;
    }

    public ChangeStatusServiceEmailSender() {
    }

    public void send() throws MessagingException {
        if(user==null){
            log.error("You must set user before sending");
            throw new IllegalStateException("user is null");
        } else if (serviceEntity==null){
            log.error("You must set service before sending");
            throw new IllegalStateException("service is null");
        } else {
            String template = getTemplate(changeStatusServiceTempl);
            template = replace(template);
            log.info("Start building email letter");
            builder.setSubject(changeStatusServiceSubj);
            builder.setAddress(user.getEmail());
            builder.setContent(template);
            log.info("Sending email");
            sender.send(builder.generateMessage());
        }
    }

   String replace(String templ) {
        log.info("Start replacing values in email template file");
        return templ.replaceAll("%name%", user.getName())
                .replaceAll("%surname%", user.getSurname())
                .replaceAll("%service%", serviceEntity.getName())
                .replaceAll("%status%", serviceEntity.getStatus());
    }

}
