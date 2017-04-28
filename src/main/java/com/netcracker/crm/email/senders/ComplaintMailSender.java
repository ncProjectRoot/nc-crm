package com.netcracker.crm.email.senders;


import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.ComplaintStatus;
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
 * Created by Pasha on 15.04.2017.
 */
@Service
public class ComplaintMailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(ComplaintMailSender.class);

    private String acceptComplaint;
    private String changeStatusComplaint;
    private String solutionComplaint;
    private String acceptComplaintSubj;
    private String changeStatusComplaintSubj;
    private String solutionComplaintSubj;
    private String complaint;

    @Qualifier("emailProps")
    @Autowired
    private Properties properties;

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void send(EmailMap emailMap) throws MessagingException {
        checkEmailMap(emailMap);
        Complaint complaint = getComplaint(emailMap);
        if (complaint == null) {
            log.error("Complaint can't be null");
            throw new IllegalStateException("complaint is null");
        } else {
            sendCompliant(complaint);
        }
    }

    @Override
    protected void checkEmailMap(EmailMap emailMap) {
        if (EmailType.COMPLAINT != emailMap.getEmailType()){
            throw new IncorrectEmailElementException("Expected email type COMPLAINT but type " + emailMap.getEmailType());
        }
    }

    private Complaint getComplaint(EmailMap emailMap) {
        Object o = emailMap.get("complaint");
        if (o instanceof Complaint){
            return (Complaint) o;
        }else {
            throw new IncorrectEmailElementException("Expected by key 'complaint' in map will be complaint");
        }
    }

    private String[] takeResponse(Complaint complaint) {
        if (ComplaintStatus.SOLVING.getName().equalsIgnoreCase(complaint.getStatus().getName())) {
            return new String[]{acceptComplaint, acceptComplaintSubj};
        } else if (ComplaintStatus.CLOSED.getName().equalsIgnoreCase(complaint.getStatus().getName())) {
            return new String[]{solutionComplaint, solutionComplaintSubj};
        } else {
            return new String[]{changeStatusComplaint, changeStatusComplaintSubj};
        }
    }

    private void sendCompliant(Complaint compliant) throws MessagingException {
        String[] response = takeResponse(compliant);
        String bodyText = replace(getTemplate(complaint).replace("%complaint%", response[0]), compliant);
        buildMail(compliant, response[1], bodyText);
    }

    private void buildMail(Complaint complaint, String subject, String body) throws MessagingException {
        EmailBuilder emailBuilder = new EmailBuilder();
        emailBuilder.setProperties(properties);
        log.debug("Start building email letter");
        emailBuilder.setContent(body);
        emailBuilder.setAddress(complaint.getCustomer().getEmail());
        emailBuilder.setSubject(subject);
        log.debug("Sending email");
        mailSender.send(emailBuilder.generateMessage());
    }

    private String replace(String html, Complaint complaint) {
        log.debug("Start replacing values in email template file");
        return html.replaceAll("%name%", complaint.getCustomer().getFirstName())
                .replaceAll("%surname%", complaint.getCustomer().getLastName())
                .replaceAll("%complaintTitle%", complaint.getTitle())
                .replaceAll("%complaintStatus%", complaint.getStatus().getName());
    }

    public String getAcceptComplaint() {
        return acceptComplaint;
    }

    public void setAcceptComplaint(String acceptComplaint) {
        this.acceptComplaint = acceptComplaint;
    }

    public String getChangeStatusComplaint() {
        return changeStatusComplaint;
    }

    public void setChangeStatusComplaint(String changeStatusComplaint) {
        this.changeStatusComplaint = changeStatusComplaint;
    }

    public String getSolutionComplaint() {
        return solutionComplaint;
    }

    public void setSolutionComplaint(String solutionComplaint) {
        this.solutionComplaint = solutionComplaint;
    }

    public String getAcceptComplaintSubj() {
        return acceptComplaintSubj;
    }

    public void setAcceptComplaintSubj(String acceptComplaintSubj) {
        this.acceptComplaintSubj = acceptComplaintSubj;
    }

    public String getChangeStatusComplaintSubj() {
        return changeStatusComplaintSubj;
    }

    public void setChangeStatusComplaintSubj(String changeStatusComplaintSubj) {
        this.changeStatusComplaintSubj = changeStatusComplaintSubj;
    }

    public String getSolutionComplaintSubj() {
        return solutionComplaintSubj;
    }

    public void setSolutionComplaintSubj(String solutionComplaintSubj) {
        this.solutionComplaintSubj = solutionComplaintSubj;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

}
