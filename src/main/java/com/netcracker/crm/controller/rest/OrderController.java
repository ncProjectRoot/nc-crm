package com.netcracker.crm.controller.rest;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author Karpunets
 * @since 04.05.2017
 */

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/csr/load/orders")
    public Map<String, Object> ordersCsr(OrderRowRequest orderRowRequest) throws IOException {
        return orderService.getOrdersRow(orderRowRequest);
    }

    @GetMapping("/customer/load/orders")
    public Map<String, Object> ordersCustomer(OrderRowRequest orderRowRequest, Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        User customer;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
            orderRowRequest.setCustomerId(customer.getId());
        }
        return orderService.getOrdersRow(orderRowRequest);
    }

}
