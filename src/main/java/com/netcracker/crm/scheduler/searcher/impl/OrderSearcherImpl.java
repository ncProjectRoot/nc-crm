package com.netcracker.crm.scheduler.searcher.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.scheduler.OrderSchedulerSqlGenerator;
import com.netcracker.crm.scheduler.searcher.OrderSearcher;
import com.netcracker.crm.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 12.05.2017.
 */
@Component
public class OrderSearcherImpl implements OrderSearcher {

    private final OrderDao orderDao;
    private final SessionRegistry sessionRegistry;
    private final OrderSchedulerSqlGenerator sqlGenerator;

    @Autowired
    public OrderSearcherImpl(OrderDao orderDao, SessionRegistry sessionRegistry, OrderSchedulerSqlGenerator sqlGenerator) {
        this.orderDao = orderDao;
        this.sessionRegistry = sessionRegistry;
        this.sqlGenerator = sqlGenerator;
    }

    public List<Order> searchForActivate(){
        List<User> csr = getOnlineCsrs();
        if (csr.isEmpty()){
            return null;
        }
        LocalDateTime toDate = LocalDateTime.now().plusDays(1);
        return orderDao.findAllByPrefDateAndStatus(sqlGenerator, csr, toDate, OrderStatus.PROCESSING);
    }

    @Override
    public List<Order> searchCsrOrder(Long csrId) {
        LocalDateTime toDate = LocalDateTime.now().plusDays(1);
        return orderDao.findAllByCsrId(toDate, OrderStatus.PROCESSING, csrId);
    }


    public List<User> getOnlineCsrs(){
        List principals = sessionRegistry.getAllPrincipals();
        List<User> csrList = new ArrayList<>();
        for (Object o : principals){
            if (o instanceof UserDetailsImpl){
                User user = (User) o;
                if (user.getUserRole() == UserRole.ROLE_CSR){
                    csrList.add(user);
                }
            }
        }
        return csrList;
    }
}
