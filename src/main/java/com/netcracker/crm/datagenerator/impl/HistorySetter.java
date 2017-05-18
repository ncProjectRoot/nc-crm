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
        for (int i = 0; i < 3; i++) {
            OrderStatus orderStatus = OrderStatus.values()[i];
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
        if (order.getStatus() == OrderStatus.PAUSED || order.getStatus() == OrderStatus.DISABLED) {
            endCycle(order, days, buffer);
        } else if (order.getStatus() == OrderStatus.ACTIVE && Math.random() > 0.7) {
            endActiveAllCycle(order, days);
        } else if (order.getStatus() == OrderStatus.REQUEST_TO_RESUME) {
            endRequestPauseCycle(order, buffer);
            days = getRand(days);
            endPauseCycle(order, LocalDateTime.now().minusDays(days));
            days = getRand(days);
            endResumeCycle(order, LocalDateTime.now().minusDays(days));
        } else if (order.getStatus() == OrderStatus.REQUEST_TO_PAUSE) {
            endRequestPauseCycle(order, buffer);
        } else if (order.getStatus() == OrderStatus.REQUEST_TO_DISABLE) {
            endRequestDisableCycle(order, buffer);
        }
    }


    private void endActiveAllCycle(Order order, int days) {
        days = getRand(days);
        endRequestPauseCycle(order, LocalDateTime.now().minusDays(days));
        days = getRand(days);
        endPauseCycle(order, LocalDateTime.now().minusDays(days));
        days = getRand(days);
        endResumeCycle(order, LocalDateTime.now().minusDays(days));
        days = getRand(days);
        endActivateCycle(order, LocalDateTime.now().minusDays(days));
    }

    private void endResumeCycle(Order order, LocalDateTime buffer) {
        History history = generateObject();
        history.setOrder(order);
        history.setNewStatus(OrderStatus.REQUEST_TO_RESUME);
        history.setDateChangeStatus(buffer);
        historyDao.create(history);
    }

    private void endActivateCycle(Order order, LocalDateTime buffer) {
        History history = generateObject();
        history.setOrder(order);
        history.setNewStatus(OrderStatus.ACTIVE);
        history.setDateChangeStatus(buffer);
        historyDao.create(history);
    }

    private void endPauseCycle(Order order, LocalDateTime buffer) {
        History history = generateObject();
        history.setOrder(order);
        history.setNewStatus(OrderStatus.PAUSED);
        history.setDateChangeStatus(buffer);
        historyDao.create(history);
    }

    private void endRequestPauseCycle(Order order, LocalDateTime buffer) {
        History history = generateObject();
        history.setOrder(order);
        history.setNewStatus(OrderStatus.REQUEST_TO_PAUSE);
        history.setDateChangeStatus(buffer);
        historyDao.create(history);
    }

    private void endRequestDisableCycle(Order order, LocalDateTime buffer) {
        History history = generateObject();
        history.setOrder(order);
        history.setNewStatus(OrderStatus.REQUEST_TO_DISABLE);
        history.setDateChangeStatus(buffer);
        historyDao.create(history);
    }

    private void endCycle(Order order, int days, LocalDateTime buffer) {
        History history = generateObject();
        history.setOrder(order);
        setEndCycleStatus(order, history);
        history.setDateChangeStatus(buffer);
        historyDao.create(history);
        days = getRand(days);
        buffer = LocalDateTime.now().minusDays(days);
        history = generateObject();
        history.setOrder(order);
        history.setNewStatus(order.getStatus());
        history.setDateChangeStatus(buffer);
        historyDao.create(history);
    }

    private void setEndCycleStatus(Order order, History history) {
        if (order.getStatus() == OrderStatus.PAUSED) {
            history.setNewStatus(OrderStatus.REQUEST_TO_PAUSE);
        } else if (order.getStatus() == OrderStatus.DISABLED) {
            history.setNewStatus(OrderStatus.REQUEST_TO_DISABLE);
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
        if (index <= 0) {
            return 0;
        }
        while (true) {
            int rand = random.nextInt(index);
            if (rand >= 0) {
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
