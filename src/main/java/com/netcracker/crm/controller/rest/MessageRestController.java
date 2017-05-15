package com.netcracker.crm.controller.rest;

import com.netcracker.crm.scheduler.OrderViewDto;
import com.netcracker.crm.scheduler.service.OrderSchedulerService;
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

    private final OrderSchedulerService schedulerService;

    @Autowired
    public MessageRestController(OrderSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping("/messages/activate")
    @PreAuthorize("hasAnyRole('ROLE_CSR')")
    public List<OrderViewDto> fetchActivateMessages(Authentication authentication) {
        return schedulerService.getCsrOrder(authentication);
    }

    @GetMapping("/messages/pause")
    @PreAuthorize("hasAnyRole('ROLE_CSR')")
    public List<OrderViewDto> fetchPauseMessages(Authentication authentication) {
        return schedulerService.getCsrPauseOrder(authentication);
    }

    @GetMapping("/messages/resume")
    @PreAuthorize("hasAnyRole('ROLE_CSR')")
    public List<OrderViewDto> fetchResumeMessages(Authentication authentication) {
        return schedulerService.getCsrResumeOrder(authentication);
    }

    @GetMapping("/messages/disable")
    @PreAuthorize("hasAnyRole('ROLE_CSR')")
    public List<OrderViewDto> fetchDisableMessages(Authentication authentication) {
        return schedulerService.getCsrDisableOrder(authentication);
    }

    @GetMapping("/messages/count")
    @PreAuthorize("hasAnyRole('ROLE_CSR')")
    public Integer checkCountOrders(Authentication authentication) {
        return schedulerService.getCsrOrderCount(authentication);
    }
}
