package com.netcracker.crm.controller.base;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    }

}