package com.netcracker.crm.email.senders;


import com.netcracker.crm.email.builder.EmailBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Created by Pasha on 15.04.2017.
 */
@Service
@Scope("prototype")
public class ComplaintMailSender extends AbstractEmailSender {

    private static final Logger log = LoggerFactory.getLogger(ComplaintMailSender.class);

    private String acceptComplaint;
    private String changeStatusComplaint;
    private String solutionComplaint;
    private String acceptComplaintSubj;
    private String changeStatusComplaintSubj;
    private String solutionComplaintSubj;
    private String feedback;

    @Autowired
    private EmailBuilder emailBuilder;

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendMail(Complaint complaint) throws MessagingException {
        sendCompliant(complaint);
    }

    private String[] takeResponse(Complaint complaint){
        if ("accept".equalsIgnoreCase(complaint.getStatus())){
            return new String[] {acceptComplaint, acceptComplaintSubj};
        }else if ("solved".equalsIgnoreCase(complaint.getStatus())){
            return new String[] {solutionComplaint, solutionComplaintSubj};
        }else {
            return new String[] {changeStatusComplaint, changeStatusComplaintSubj};
        }
    }

    private void sendCompliant(Complaint compliant) throws MessagingException {
        String [] response = takeResponse(compliant);
        String bodyText = replace(compliant, getTemplate(feedback).replace("%feedback%", response[0]));
        buildMail(compliant, response[1], bodyText);
    }




    private void buildMail(Complaint complaint, String subject, String body) throws MessagingException {
        log.info("Start building email letter");
        emailBuilder.setContent(body);
        emailBuilder.setAddress(complaint.getSender().getEmail());
        emailBuilder.setSubject(subject);
        log.info("Sending email");
        mailSender.send(emailBuilder.generateMessage());
    }

    private String replace(Complaint complaint, String html) {
        log.info("Start replacing values in email template file");
        return html.replaceAll("%name%", complaint.getSender().getName())
                .replaceAll("%surname%", complaint.getSender().getSurname())
                .replaceAll("%complaintName%", complaint.getName())
                .replaceAll("%complaintStatus%", complaint.getStatus());
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
