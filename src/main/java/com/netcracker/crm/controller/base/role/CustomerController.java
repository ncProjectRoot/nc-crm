package com.netcracker.crm.controller.base.role;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.ComplaintService;
import com.netcracker.crm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author Karpunets
 * @since 25.04.2017
 */

@RequestMapping(value = "/ROLE_CUSTOMER")
@Controller
public class CustomerController {

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Map<String, Object> model) {
        return "dashboard/customer";
    }

    @GetMapping("/users")
    public String users(Map<String, Object> model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
            if (user.getUserRole() == UserRole.ROLE_CUSTOMER && user.isContactPerson()) {
                return "users";
            }
        }
        return "error";
    }

    @GetMapping("/orders")
    public String orders(Map<String, Object> model) {
        return "orders";
    }

    @GetMapping("/products")
    public String products(Map<String, Object> model) {
        return "products";
    }

    @GetMapping("/complaints")
    public String complaints(Map<String, Object> model, Authentication authentication) {
        Long customerId = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            User customer = (UserDetailsImpl) principal;
            customerId = customer.getId();
        } else {
            //!production
            customerId = 1L;
        }
        List<Order> orders = orderService.findByCustomerId(customerId);
        model.put("orders", orders);
        return "complaints";
    }

}
