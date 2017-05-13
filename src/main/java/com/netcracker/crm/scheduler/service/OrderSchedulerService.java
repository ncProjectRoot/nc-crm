package com.netcracker.crm.scheduler.service;

import com.netcracker.crm.scheduler.OrderViewDto;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Created by Pasha on 13.05.2017.
 */
public interface OrderSchedulerService {
    List<OrderViewDto> getCsrOrder(Authentication authentication);
    Integer getCsrOrderCount(Authentication authentication);
}
