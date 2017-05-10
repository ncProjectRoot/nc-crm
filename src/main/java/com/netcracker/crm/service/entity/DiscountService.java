package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.request.DiscountRowRequest;
import com.netcracker.crm.dto.DiscountDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface DiscountService {
    List<Discount> getDiscountByTitle(String title);

    Discount persist(DiscountDto discountDto);

    Map<String, Object> getDiscounts(DiscountRowRequest rowRequest);

    Discount getDiscountById(Long id);

    boolean updateDiscount(DiscountDto discountDto);
}
