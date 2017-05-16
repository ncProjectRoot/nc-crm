package com.netcracker.crm.listener.event;

import com.netcracker.crm.domain.model.Complaint;
import org.springframework.context.ApplicationEvent;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 13.05.2017
 */
public class ChangeStatusComplaintEvent extends ApplicationEvent {

    private Complaint complaint;
    private boolean isDone;


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ChangeStatusComplaintEvent(Object source, Complaint complaint) {
        super(source);
        this.complaint = complaint;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
