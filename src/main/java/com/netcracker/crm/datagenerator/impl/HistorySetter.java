package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Pasha on 16.05.2017.
 */
@Service
public class HistorySetter extends AbstractSetter<History> {

    private List<Order> orders;
    private List<Complaint> complaints;
    private List<Product> products;

    private final HistoryDao historyDao;

    @Autowired
    public HistorySetter(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    @Override
    public List<History> generate(int numbers) {
        generateOrderHistory();
        generateComplaintsHistory();
        generateProductHistory();
        return null;
    }

    @Override
    @Transactional
    public History generateObject() {
        History history = new History();
        history.setDescChangeStatus(randomString.nextString());
        return history;
    }


    private void generateOrderHistory() {
        for (Order order : orders) {
            orderCycle(order);
        }
    }

    private void generateComplaintsHistory() {
        for (Complaint complaint : complaints) {
            complaintCycle(complaint);
        }
    }

    private void generateProductHistory() {
        for (Product product : products) {
            productCycle(product);
        }
    }

    private void orderCycle(Order order) {
        int days = LocalDateTime.now().getDayOfYear() - order.getDate().getDayOfYear();
        LocalDateTime buffer = order.getDate();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            History history = generateObject();
            history.setOrder(order);
            history.setNewStatus(orderStatus);
            history.setDateChangeStatus(buffer);
            historyDao.create(history);
            if (order.getStatus() == orderStatus) {
                break;
            }
            days = getRand(days);
            buffer = LocalDateTime.now().minusDays(days);
        }
    }

    private void complaintCycle(Complaint complaint) {
        int days = LocalDateTime.now().getDayOfYear() - complaint.getDate().getDayOfYear();
        LocalDateTime buffer = complaint.getDate();
        for (ComplaintStatus complaintStatus : ComplaintStatus.values()) {
            History history = generateObject();
            history.setComplaint(complaint);
            history.setNewStatus(complaintStatus);
            history.setDateChangeStatus(buffer);
            historyDao.create(history);
            if (complaint.getStatus() == complaintStatus) {
                break;
            }
            days = getRand(days);
            buffer = LocalDateTime.now().minusDays(days);
        }
    }

    private void productCycle(Product product) {
        int days = getRand(365);
        LocalDateTime buffer = LocalDateTime.now().minusDays(days);
        for (ProductStatus productStatus : ProductStatus.values()) {
            History history = generateObject();
            history.setProduct(product);
            history.setNewStatus(productStatus);
            history.setDateChangeStatus(buffer);
            historyDao.create(history);
            if (product.getStatus() == productStatus) {
                break;
            }
            days = getRand(days);
            buffer = LocalDateTime.now().minusDays(days);
        }
    }

    private int getRand(int index) {
        if (index <= 0){
            return 0;
        }
        while (true){
            int rand = random.nextInt(index);
            if (rand >= 0){
                return rand;
            }
        }
    }


    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
