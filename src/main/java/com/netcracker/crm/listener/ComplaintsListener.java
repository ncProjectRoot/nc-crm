package com.netcracker.crm.listener;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.ComplaintStatus;
import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.listener.event.ChangeStatusComplaintEvent;
import com.netcracker.crm.listener.event.CreateComplaintEvent;
import com.netcracker.crm.service.email.AbstractEmailSender;
import com.netcracker.crm.service.email.EmailParam;
import com.netcracker.crm.service.email.EmailParamKeys;
import com.netcracker.crm.service.email.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 13.05.2017
 */

@Component
public class ComplaintsListener {

    private HistoryDao historyDao;
    private AbstractEmailSender emailSender;
    private ComplaintDao complaintDao;

    @Autowired
    public ComplaintsListener(HistoryDao historyDao, ComplaintDao complaintDao,
                              @Qualifier("complaintSender") AbstractEmailSender emailSender) {
        this.historyDao = historyDao;
        this.complaintDao = complaintDao;
        this.emailSender = emailSender;
    }

    @EventListener
    public void createComplaint(CreateComplaintEvent event) {
        Complaint complaint = event.getComplaint();
        ComplaintStatus status = complaint.getStatus();
        History history = new History();
        history.setDateChangeStatus(LocalDateTime.now());
        history.setOldStatus(status);
        String role = complaint.getCustomer().getUserRole().getName();
        role = role.substring(role.indexOf("_") + 1);

        history.setDescChangeStatus( role + " with id " +
                complaint.getCustomer().getId() + " created complaint");
        history.setComplaint(complaint);
        historyDao.create(history);
        sendMail(complaint);
    }

    @EventListener(condition = "#event.complaint.status.name.equals('OPEN') " +
            "&& #event.done==false")
    public void acceptComplaint(ChangeStatusComplaintEvent event) {
        Complaint complaint = event.getComplaint();
        complaint.setStatus(ComplaintStatus.SOLVING);
        History history = new History();
        history.setDateChangeStatus(LocalDateTime.now());
        history.setOldStatus(complaint.getStatus());
        String role = complaint.getPmg().getUserRole().getName();
        role = role.substring(role.indexOf("_") + 1);

        history.setDescChangeStatus(role + " with id " +
                complaint.getPmg().getId() + " accepted complaint");
        history.setComplaint(complaint);
        complaintDao.update(complaint);
        historyDao.create(history);
        sendMail(complaint);
        event.setDone(true);
    }

    @EventListener(condition = "#event.complaint.status.name.equals('SOLVING') " +
            "&& #event.done==false")
    public void closeComplaint(ChangeStatusComplaintEvent event) {
        Complaint complaint = event.getComplaint();
        complaint.setStatus(ComplaintStatus.CLOSED);
        History history = new History();
        history.setDateChangeStatus(LocalDateTime.now());
        history.setOldStatus(complaint.getStatus());
        String role = complaint.getPmg().getUserRole().getName();
        role = role.substring(role.indexOf("_") + 1);

        history.setDescChangeStatus(role + " with id " +
                complaint.getPmg().getId() + " closed complaint");
        history.setComplaint(complaint);
        complaintDao.update(complaint);
        historyDao.create(history);
        sendMail(complaint);
        event.setDone(true);
    }

    private void sendMail(Complaint complaint){
        EmailParam emailMap = new EmailParam(EmailType.COMPLAINT);
        emailMap.put(EmailParamKeys.COMPLAINT, complaint);
        try {
            emailSender.send(emailMap);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
