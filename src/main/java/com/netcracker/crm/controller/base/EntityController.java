package com.netcracker.crm.controller.base;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.entity.OrderService;
import com.netcracker.crm.service.entity.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Karpunets
 * @since 07.05.2017
 */

@Controller
public class EntityController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/*/complaint/{id}")
    public String complaint(Map<String, Object> model, @PathVariable("id") Long id,
                            Authentication authentication) {
        User customer = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
        }
        boolean allowed = complaintService.checkAccess(customer, id);
        if (allowed) {
            Complaint complaint = complaintService.findById(id);
            model.put("complaint", complaint);
            return "complaint";
        } else {
            return "403";
        }
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/*/product", method = {RequestMethod.GET})
    public String product(Map<String, Object> model, Authentication authentication,
                       @RequestParam Long id) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        }
//        model.put("user", user);
        model.put("product", productService.getProductsById(id));
        return "product";
    }

    @RequestMapping(value = "/*/order", method = {RequestMethod.GET})
    public String order(Map<String, Object> model, Authentication authentication,
                       @RequestParam Long id) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        }
//        model.put("user", user);
        model.put("order", orderService.getOrderById(id));
        return "order";
    }

}