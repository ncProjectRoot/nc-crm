package com.netcracker.crm.dto.mapper.impl;

import com.netcracker.crm.domain.model.Region;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.mapper.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
@Component
public class RegionMapper {

    public Mapper<Region, AutocompleteDto> modelToAutocomplete() {
        return (model, autocompleteDto) -> {
            autocompleteDto.setId(model.getId());
            autocompleteDto.setValue(model.getName());
        };
    }
}
