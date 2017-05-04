package com.netcracker.crm.controller.rest;

import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.dto.GroupDto;
import com.netcracker.crm.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Pasha on 01.05.2017.
 */
@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/csr/groupByName/{name}", method = RequestMethod.GET)
    public List<Group> discountByTitle(@PathVariable String name){
        return groupService.groupsByName(name);
    }

    @PostMapping("/csr/addGroup")
    public String addGroup(GroupDto groupDto){
        Group group = groupService.persist(groupDto);
        return "Group with " + group.getName() + " id successful added";
    }
}
