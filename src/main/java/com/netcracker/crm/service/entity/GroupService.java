package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.request.GroupRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.GroupDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface GroupService {

    Group create(GroupDto groupDto);

    List<AutocompleteDto> getAutocompleteGroup(String pattern);
    Map<String, Object> getGroupPage(GroupRowRequest request);
    Group getGroupById(Long id);
    boolean update(GroupDto groupDto);

}
