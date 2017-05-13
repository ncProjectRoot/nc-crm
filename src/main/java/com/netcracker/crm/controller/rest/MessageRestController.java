package com.netcracker.crm.controller.rest;

import com.netcracker.crm.scheduler.service.OrderSchedulerService;
import com.netcracker.crm.scheduler.OrderViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Pasha on 13.05.2017.
 */
@RestController
public class MessageRestController {

    @Autowired
    private OrderSchedulerService schedulerService;

    @GetMapping("/messages")
    @PreAuthorize("hasAnyRole('ROLE_CSR')")
    public List<OrderViewDto> fetchCsrMessages(Authentication authentication){
        return schedulerService.getCsrOrder(authentication);
    }



    @GetMapping("/messages/count")
    @PreAuthorize("hasAnyRole('ROLE_CSR')")
    public Integer checkCountOrders(Authentication authentication){
        return schedulerService.getCsrOrderCount(authentication);
    }
}
