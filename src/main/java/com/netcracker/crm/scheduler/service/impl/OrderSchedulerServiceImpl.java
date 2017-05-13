package com.netcracker.crm.scheduler.service.impl;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.scheduler.cacher.impl.OrderCache;
import com.netcracker.crm.scheduler.OrderViewDto;
import com.netcracker.crm.scheduler.service.OrderSchedulerService;
import com.netcracker.crm.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 13.05.2017.
 */
@Service
public class OrderSchedulerServiceImpl implements OrderSchedulerService {
    @Autowired
    private OrderCache orderCache;


    @Override
    public List<OrderViewDto> getCsrOrder(Authentication authentication) {
        Object o = authentication.getPrincipal();
        if (o instanceof UserDetailsImpl){
            Long csrId = ((UserDetailsImpl) o).getId();
            return convertMapToList(orderCache.getElement(csrId));
        }
        return null;
    }

    @Override
    public Integer getCsrOrderCount(Authentication authentication) {
        Object o = authentication.getPrincipal();
        if (o instanceof UserDetailsImpl){
            Long csrId = ((UserDetailsImpl) o).getId();
            return orderCache.getCountElements(csrId);
        }
        return 0;
    }


    private List<OrderViewDto> convertMapToList(Map<Long, Order> map){
        List<OrderViewDto> orders = new ArrayList<>();
        for (Map.Entry<Long, Order> m : map.entrySet()){
            orders.add(new OrderViewDto(m.getValue()));
        }
        return orders;
    }
}
