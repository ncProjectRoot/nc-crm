package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.request.DiscountRowRequest;

import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public interface DiscountDao extends CrudDao<Discount> {
    List<Discount> findByTitle(String title);

    Long getCount();

    Long getDiscountRowsCount(DiscountRowRequest rowRequest);

    List<Discount> findDiscounts(DiscountRowRequest rowRequest);
    List<Discount> findByIdOrTitle(String pattern);
}
