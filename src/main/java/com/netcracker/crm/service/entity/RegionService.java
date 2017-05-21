package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Region;
import com.netcracker.crm.dto.AutocompleteDto;

import java.util.List;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
public interface RegionService {
    Region getRegionById(Long id);

    List<AutocompleteDto> getAutocompleteDto(String pattern);
}
