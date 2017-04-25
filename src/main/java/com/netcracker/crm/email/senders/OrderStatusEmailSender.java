package com.netcracker.crm.email.senders;


import com.netcracker.crm.domain.model.Order;
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
public class OrderStatusEmailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(OrderStatusEmailSender.class);

    //Name of template html file for email letter
    private String orderStatusTempl;
    //Subject for email letter
    private String orderStatusSubj;

    @Autowired
    private EmailBuilder builder;

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

    public void send(Order order) throws MessagingException {
        if (order == null) {
            log.error("Order can't be null");
            throw new IllegalStateException("order is null");
        } else {
            String template = getTemplate(orderStatusTempl);
            template = replace(template, order);
            log.debug("Start building  email letter");
            builder.setSubject(orderStatusSubj);
            builder.setAddress(order.getCustomer().getEmail());
            builder.setContent(template);
            log.debug("Sending email");
            sender.send(builder.generateMessage());
        }
    }

    private String replace(String templ, Order order) {
        log.debug("Start replacing values in email template file");
        return templ.replaceAll("%name%", order.getCustomer().getFirstName())
                .replaceAll("%surname%", order.getCustomer().getLastName())
                .replaceAll("%service%", order.getProduct().getTitle())
                .replaceAll("%status%", order.getStatus().name());
    }

}
