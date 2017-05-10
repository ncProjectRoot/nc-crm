package com.netcracker.crm.controller.base.role;

import com.netcracker.crm.service.entity.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author Karpunets
 * @since 25.04.2017
 */

@RequestMapping(value = "/ROLE_PMG")
@Controller
public class PMGController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Map<String, Object> model) {
        return "dashboard/pmg";
    }

    @GetMapping("/profile")
    public String profile(Map<String, Object> model) {
        return "profile";
    }

    @GetMapping("/complaints")
    public String complaints(Map<String, Object> model) {
        return "complaints";
    }

}
