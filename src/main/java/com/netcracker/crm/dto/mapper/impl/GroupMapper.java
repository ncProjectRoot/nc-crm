package com.netcracker.crm.dto.mapper.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.real.RealGroup;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.GroupDto;
import com.netcracker.crm.dto.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
@Component
public class GroupMapper {

    private final DiscountDao discountDao;

    @Autowired
    public GroupMapper(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    public Mapper<GroupDto, RealGroup> dtoToModel() {
        return (dto, model) -> {
            model.setId(dto.getId());
            model.setName(dto.getName());
            Discount discount = dto.getDiscountId() > 0 ? discountDao.findById(dto.getDiscountId()) : null;
            model.setDiscount(discount);
        };
    }

    public Mapper<Group, AutocompleteDto> modelToAutocomplete() {
        return (model, autocompleteDto) -> {
            autocompleteDto.setId(model.getId());
            autocompleteDto.setValue(model.getName());
        };
    }
}
