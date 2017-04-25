package com.netcracker.crm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.crm.domain.PageInformation;
import com.netcracker.crm.domain.model.UserRole;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Karpunets
 * @since 24.04.2017
 */

@Component
public class PageInformationService {

    private static final String FILE_NAME = "static/json/pages.json";
    private List<PageInformation> pageInformationList;
    private HashMap<UserRole, List<PageInformation>> roleMap;

    public PageInformationService() {
        roleMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new ClassPathResource(FILE_NAME).getFile();
            pageInformationList = objectMapper.readValue(file, new TypeReference<List<PageInformation>>(){});
            System.out.println(pageInformationList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PageInformation> getPageMenu(UserRole role) {
        if (roleMap.containsKey(role)) {
            return roleMap.get(role);
        }
        List<PageInformation> result = new ArrayList<>();
        for (PageInformation pageInformation: pageInformationList) {
            if (pageInformation.getRoles().contains(role)) {
                result.add(pageInformation);
            }
        }
        roleMap.put(role, result);
        return result;
    }

    public PageInformation getPageInformation(String href) {
        for (PageInformation pageInformation: pageInformationList) {
            if (href.equals(pageInformation.getHref())) {
                return pageInformation;
            }
        }
        return null;
    }

}