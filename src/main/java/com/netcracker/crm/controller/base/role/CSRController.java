package com.netcracker.crm.controller.base.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author Karpunets
 * @since 25.04.2017
 */

@RequestMapping(value = "/ROLE_CSR")
@Controller
public class CSRController {

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Map<String, Object> model) {
        return "dashboardCSR";
    }

    @GetMapping("/create")
    public String create(Map<String, Object> model) {
        return "create";
    }
    
    @GetMapping("/profile")
    public String profile(Map<String, Object> model) {
        return "profile";
    }

    @GetMapping("/products")
    public String products(Map<String, Object> model) {
        return "products";
    }

    @GetMapping("/orders")
    public String orders(Map<String, Object> model) {
        return "orders";
    }

}
