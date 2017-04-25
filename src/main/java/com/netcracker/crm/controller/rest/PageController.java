package com.netcracker.crm.controller.rest;

import com.netcracker.crm.domain.PageInformation;
import com.netcracker.crm.service.PageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Karpunets
 * @since 25.04.2017
 */

@Controller
public class PageController {

    @Autowired
    private PageInformationService pageInformationService;

    @GetMapping("/page-information")
    public @ResponseBody
    PageInformation pageInformation(@RequestParam String href) {
        return pageInformationService.getPageInformation(href);
    }

}
