package com.netcracker.crm.controller;

import java.util.ArrayList;
import java.util.Map;

import com.netcracker.crm.domain.PageInformation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Map<String, Object> model) {

        model.put("newMessage", 5);
        model.put("currentLanguage", "English");

        //need fix
        ArrayList<PageInformation> jstlMenuElements = new ArrayList<>();
        jstlMenuElements.add(new PageInformation("Home", "home", "/home"));
        jstlMenuElements.add(new PageInformation("My Profile", "person_pin", "/profile"));
        jstlMenuElements.add(new PageInformation("Create User", "queue", "/createUser"));
        jstlMenuElements.add(new PageInformation("Search", "search", "/search"));
        jstlMenuElements.add(new PageInformation("Profit", "business", "/profit"));

        model.put("menuElements", jstlMenuElements);

        return "main";
    }

    @GetMapping("/page-information")
    public @ResponseBody
    PageInformation pageInformation(@RequestParam String hash) {
        System.out.println(hash);
        //need fix
        switch (hash) {
            case "":
                return new PageInformation("Home", "home", "/home");
            case "home":
                return new PageInformation("Home", "home", "/home");
            case "profile":
                return new PageInformation("My Profile", "person_pin", "/profile");
            case "createUser":
                return new PageInformation("Create User", "queue", "/createUser");
        }
        return null;
    }

}