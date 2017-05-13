package com.netcracker.crm.scheduler.searcher;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;

import java.util.List;

/**
 * Created by Pasha on 12.05.2017.
 */
public interface OrderSearcher {
    List<Order> searchForActivate();
    List<Order> searchCsrOrder(Long csrId);
    List<User> getOnlineCsrs();
}
