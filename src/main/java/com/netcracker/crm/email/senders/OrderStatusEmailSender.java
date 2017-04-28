package com.netcracker.crm.email.senders;


import com.netcracker.crm.domain.model.Order;
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
public class OrderStatusEmailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(OrderStatusEmailSender.class);

    //Name of template html file for email letter
    private String orderStatusTempl;
    //Subject for email letter
    private String orderStatusSubj;

    @Qualifier("emailProps")
    @Autowired
    private Properties properties;

    @Autowired
    private JavaMailSenderImpl sender;

    public String getOrderStatusTempl() {
        return orderStatusTempl;
    }

    public void setOrderStatusTempl(String orderStatusTempl) {
        this.orderStatusTempl = orderStatusTempl;
    }

    public String getOrderStatusSubj() {
        return orderStatusSubj;
    }

    public void setOrderStatusSubj(String orderStatusSubj) {
        this.orderStatusSubj = orderStatusSubj;
    }

    public OrderStatusEmailSender() {
    }

    public void send(EmailMap emailMap) throws MessagingException {
        checkEmailMap(emailMap);
        Order order = getOrder(emailMap);

        String template = replace(getTemplate(orderStatusTempl), order);
        EmailBuilder emailBuilder = new EmailBuilder();
        System.out.println(properties);
        emailBuilder.setProperties(properties);
        log.debug("Start building  email letter");
        emailBuilder.setSubject(orderStatusSubj);
        emailBuilder.setAddress(order.getCustomer().getEmail());
        emailBuilder.setContent(template);
        log.debug("Sending email");
        sender.send(emailBuilder.generateMessage());
    }

    private Order getOrder(EmailMap emailMap) {
        Object o = emailMap.get("order");
        if (o instanceof Order){
            return (Order) o;
        }else {
            throw new IncorrectEmailElementException("Expected by key 'order' in map will be order");
        }
    }

    private String replace(String templ, Order order) {
        log.debug("Start replacing values in email template file");
        return templ.replaceAll("%name%", order.getCustomer().getFirstName())
                .replaceAll("%surname%", order.getCustomer().getLastName())
                .replaceAll("%service%", order.getProduct().getTitle())
                .replaceAll("%status%", order.getStatus().name());
    }

    @Override
    protected void checkEmailMap(EmailMap emailMap) {
        if (EmailType.ORDER_STATUS != emailMap.getEmailType()){
            throw new IncorrectEmailElementException("Expected email type ORDER_STATUS but type " + emailMap.getEmailType());
        }
    }
}
