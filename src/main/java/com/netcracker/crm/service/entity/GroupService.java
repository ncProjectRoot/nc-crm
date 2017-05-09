package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.GroupDto;

import java.util.List;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface GroupService {

    List<Group> groupsByName(String name);
    Group persist(GroupDto groupDto);

    List<AutocompleteDto> getAutocompleteGroup(String pattern);
}
