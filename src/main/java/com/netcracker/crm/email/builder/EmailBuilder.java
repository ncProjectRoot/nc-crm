package com.netcracker.crm.email.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Pasha on 14.04.2017.
 */

public class EmailBuilder {

    private static final Logger log = LoggerFactory.getLogger(EmailBuilder.class);

    private String username = null;
    private String password = null;

    private String subject = "";
    private List<InternetAddress> addresses;
    private Multipart multipart;
    private Properties properties;

    public EmailBuilder(String username, String password, Properties properties) {

        this.username = username;
        this.password = password;
        this.properties = properties;
        addresses = new ArrayList<>();
        multipart = new MimeMultipart();
    }

    public MimeMessage generateMessage() throws MessagingException {
        log.info("Generating message");
        MimeMessage message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(username));
        message.addRecipients(Message.RecipientType.TO, addresses.toArray(new InternetAddress[addresses.size()]));
        message.setSubject(subject);
        message.setContent(multipart);
        multipart = new MimeMultipart();
        addresses.clear();
        subject = "";
        return message;
    }

    public void setContent(Object obj) throws MessagingException {
        setContent(obj, "text/html; charset=utf-8");
    }

    public void setContent(Object obj, String type) throws MessagingException {
        log.info("Setting email content");
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(obj, type);
        multipart.addBodyPart(bodyPart);
    }

    public void setFile(String path) throws MessagingException {
        File file = new File(path);
        setFile(file);
    }

    public void setFile(File file) throws MessagingException {
        log.info("Setting attaching file");
        MimeBodyPart bodyPart = new MimeBodyPart();
        DataSource dataSource = new FileDataSource(file);
        bodyPart.setDataHandler(new DataHandler(dataSource));
        bodyPart.setFileName(file.getName());
        multipart.addBodyPart(bodyPart);
    }


    private Session getSession() {
        return Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void setAddress(InternetAddress address){
        addresses.add(address);
    }

    public void setAddress(String address) throws AddressException {
        setAddress(new InternetAddress(address));
    }

    public void setAllAddress(List<InternetAddress> address){
        addresses.addAll(address);
    }

    public void setAllAddress(String[] address) throws AddressException {
        for (String mail : address) {
            addresses.add(new InternetAddress(mail));
        }
    }

    public void setAllAddress(InternetAddress[] address){
        addresses.addAll(Arrays.asList(address));
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}