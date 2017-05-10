package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.request.GroupRowRequest;
import com.netcracker.crm.dto.GroupDto;
import com.netcracker.crm.service.entity.GroupService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_SERVER_ERROR;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_GROUP_CREATED;

/**
 * Created by Pasha on 01.05.2017.
 */
@RestController
public class GroupRestController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private ResponseGenerator<Group> generator;
    @Autowired
    private GroupValidator groupValidator;
    @Autowired
    private BindingResultHandler bindingResultHandler;

    @RequestMapping(value = "/csr/groupByName/{name}", method = RequestMethod.GET)
    public List<Group> discountByTitle(@PathVariable String name){
        return groupService.groupsByName(name);
    }

    @PostMapping("/csr/addGroup")
    public ResponseEntity<?> addGroup(@Valid GroupDto groupDto, BindingResult bindingResult){
        groupValidator.validate(groupDto, bindingResult);
        if (bindingResult.hasErrors()){
            return bindingResultHandler.handle(bindingResult);
        }

        Group group = groupService.persist(groupDto);

        if (group.getId() > 0){
            return generator.getHttpResponse(SUCCESS_MESSAGE,SUCCESS_GROUP_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(SUCCESS_MESSAGE,ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/groups")
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getGroups(GroupRowRequest request){
        return new ResponseEntity<>(groupService.getGroupPage(request), HttpStatus.OK);
    }


    @GetMapping("/groups/name")
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<List<String>> groupNames(@RequestParam String likeTitle){
        return new ResponseEntity<>(groupService.groupByName(likeTitle), HttpStatus.OK);
    }

}
