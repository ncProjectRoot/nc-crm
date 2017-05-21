package com.netcracker.crm.service.entity;

import com.netcracker.crm.dto.AutocompleteDto;

import java.util.List;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
public interface RegionService {
    Object getRegionById(Long id);

    List<AutocompleteDto> getAutocompleteDto(String pattern);
}
