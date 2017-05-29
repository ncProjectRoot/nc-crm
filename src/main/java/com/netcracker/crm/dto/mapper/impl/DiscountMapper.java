package com.netcracker.crm.dto.mapper.impl;

import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.real.RealDiscount;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.DiscountDto;
import com.netcracker.crm.dto.mapper.Mapper;
import com.netcracker.crm.dto.row.DiscountRowDto;
import org.springframework.stereotype.Component;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
@Component
public class DiscountMapper {

    public Mapper<DiscountDto, RealDiscount> dtoToModel() {
        return (dto, model) -> {
            model.setId(dto.getId());
            model.setTitle(dto.getTitle());
            if (dto.getActive() == null) {
                model.setActive(false);
            } else {
                model.setActive(dto.getActive());
            }
            model.setPercentage(dto.getPercentage());
            model.setDescription(dto.getDescription());

        };
    }

    public Mapper<Discount, DiscountRowDto> modelToRowDto() {
        return (model, rowDto) -> {
            rowDto.setId(model.getId());
            rowDto.setTitle(model.getTitle());
            rowDto.setPercentage(model.getPercentage());
            rowDto.setDiscountActive(model.isActive());
            rowDto.setDescription(model.getDescription());
        };
    }

    public Mapper<Discount, AutocompleteDto> modelToAutocomplete() {
        return (model, autocompleteDto) -> {
            autocompleteDto.setId(model.getId());
            autocompleteDto.setValue(model.getTitle() + " " + model.getPercentage() + "%");
        };
    }
}
