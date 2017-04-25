package com.netcracker.crm.controller;

import java.util.Map;

import com.netcracker.crm.domain.PageInformation;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.service.PageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private PageInformationService pageInformationService;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        model.put("newMessage", 5);
        model.put("currentLanguage", "English");
        model.put("menuElements", pageInformationService.getPageMenu(UserRole.ROLE_ADMIN));

        return "main";
    }

    @GetMapping("/page-information")
    public @ResponseBody
    PageInformation pageInformation(@RequestParam String href) {
        return pageInformationService.getPageInformation(href);
    }

}