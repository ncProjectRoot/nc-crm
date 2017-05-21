package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.dao.RegionGroupsDao;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Region;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.RegionDto;
import com.netcracker.crm.service.entity.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
@Service
public class RegionServiceImpl implements RegionService {
    private static final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

    private final RegionDao regionDao;
    private final RegionGroupsDao regionGroupsDao;

    @Autowired
    public RegionServiceImpl(RegionDao regionDao, RegionGroupsDao regionGroupsDao) {
        this.regionDao = regionDao;
        this.regionGroupsDao = regionGroupsDao;
    }

    @Override
    public Region getRegionById(Long id) {
        return regionDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutocompleteDto> getAutocompleteDto(String pattern) {
        List<Region> regions = regionDao.findAllByPattern(pattern);
        return convertToAutocompletesDto(regions);
    }

    @Override
    public boolean update(RegionDto regionDto) {
        List<Group> oldGroups = regionGroupsDao.findGroupsByRegionId(regionDto.getId());
        for (Group oldGroup : oldGroups) {
            if (regionDto.getGroupIds().contains(oldGroup.getId())) {
                regionDto.getGroupIds().remove(oldGroup.getId());
            } else {
                regionGroupsDao.delete(regionDto.getId(), oldGroup.getId());
            }
        }
        for (Long newGroupId : regionDto.getGroupIds()) {
            regionGroupsDao.create(regionDto.getId(), newGroupId);
        }
        return true;
    }

    private List<AutocompleteDto> convertToAutocompletesDto(List<Region> regions) {
        List<AutocompleteDto> result = new ArrayList<>();
        for (Region region : regions) {
            AutocompleteDto autocompleteDto = new AutocompleteDto();
            autocompleteDto.setId(region.getId());
            autocompleteDto.setValue(region.getName());
            result.add(autocompleteDto);
        }
        return result;
    }
}
