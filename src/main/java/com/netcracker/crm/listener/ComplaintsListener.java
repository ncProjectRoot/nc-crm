package com.netcracker.crm.listener;

import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.ComplaintStatus;
import com.netcracker.crm.domain.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 13.05.2017
 */

@Component
public class ComplaintsListener {

    private HistoryDao historyDao;

    @Autowired
    public ComplaintsListener(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    @EventListener
    public void changeStatusComplaint(ChangeStatusComplaintEvent event) {
        Complaint complaint = event.getComplaint();
        ComplaintStatus status = complaint.getStatus();
        History history = new History();
        history.setDateChangeStatus(LocalDateTime.now());
        history.setOldStatus(status);
        if (status.equals(ComplaintStatus.OPEN)) {
            acceptComplaint(complaint, history);
        } else if (status.equals(ComplaintStatus.SOLVING)) {
            closeComplaint(complaint, history);
        }
        history.setComplaint(complaint);
        historyDao.create(history);
    }

    private void acceptComplaint(Complaint complaint, History history) {
        String role = complaint.getPmg().getUserRole().getName();
        role = role.substring(role.indexOf("_") + 1);

        complaint.setStatus(ComplaintStatus.SOLVING);
        history.setDescChangeStatus(role + " with id " +
                complaint.getPmg().getId() + " accepted complaint");
    }

    private void closeComplaint(Complaint complaint, History history) {
        String role = complaint.getPmg().getUserRole().getName();
        role = role.substring(role.indexOf("_") + 1);

        history.setDescChangeStatus(role + " with id " +
                complaint.getPmg().getId() + " closed complaint");
        complaint.setStatus(ComplaintStatus.CLOSED);
    }
}
