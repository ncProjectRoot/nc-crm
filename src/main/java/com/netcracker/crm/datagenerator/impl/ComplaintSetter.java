package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.ComplaintStatus;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 05.05.2017.
 */
@Service
public class ComplaintSetter extends AbstractSetter<Complaint> {

    private int counter;
    private List<Order> orders;
    private List<User> pmgs;


    @Autowired
    private ComplaintDao complaintDao;


    @Override
    public List<Complaint> generate(int numbers) {
        List<Complaint> complaints = new ArrayList<>();

        for (int i = 0; i < numbers; i++) {
            Complaint complaint = generateObject();
            complaintDao.create(complaint);
            complaints.add(complaint);
        }
        return complaints;
    }

    @Override
    public Complaint generateObject() {
        Complaint complaint = new Complaint();
        setUserOrderDate(complaint);
        complaint.setTitle("Complaint " + counter++);
        complaint.setMessage(randomString.nextString());
        ComplaintStatus status = getStatus();
        complaint.setStatus(status);
        if (status != ComplaintStatus.OPEN) {
            complaint.setPmg(getPmg());
        }
        return complaint;
    }




    private ComplaintStatus getStatus(){
        return ComplaintStatus.values()[random.nextInt(3)];
    }

    private User getPmg(){
        return pmgs.get(random.nextInt(pmgs.size()));
    }

    private void setUserOrderDate(Complaint complaint){
        Order order = orders.get(random.nextInt(orders.size()));
        LocalDateTime date = order.getDate();
        date.plusDays(random.nextInt(10));
        complaint.setOrder(order);
        complaint.setCustomer(order.getCustomer());
        complaint.setDate(date);
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setPmgs(List<User> pmgs) {
        this.pmgs = pmgs;
    }
}
