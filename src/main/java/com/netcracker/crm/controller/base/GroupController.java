package com.netcracker.crm.controller.base;

import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
