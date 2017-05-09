package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.DiscountDto;

import java.util.List;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface DiscountService {
    List<Discount> getProductDiscount(String title);

    Discount persist(DiscountDto discountDto);

    List<AutocompleteDto> getAutocompleteDiscount(String pattern);
}
