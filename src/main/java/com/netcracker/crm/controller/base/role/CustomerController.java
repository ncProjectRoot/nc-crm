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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
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
        User customer = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
        } else {
            //!production
            customer = new User();
            customer.setId(3L);
        }
        List<Order> orders = null;
        if (customer.isContactPerson()) {
            orders = orderService.findOrgOrdersByCustId(customer.getId());
        } else {
            orders = orderService.findByCustomerId(customer.getId());
        }
        model.put("orders", orders);
        return "complaints";
    }

    @GetMapping("/complaint/{id}")
    public String complaint(Map<String, Object> model, @PathVariable("id") Long id,
                            Authentication authentication, HttpServletResponse httpServletResponse) {
        User customer = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
        }
        boolean allowed = complaintService.checkAccess(customer, id);
        if(allowed) {
            Complaint complaint = complaintService.findById(id);
            model.put("complaint", complaint);
            return "complaint";
        } else {
            return "403";
        }
    }

}
