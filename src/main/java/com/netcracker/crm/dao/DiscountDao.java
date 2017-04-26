package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Discount;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public interface DiscountDao {
    long create(Discount discount);

    long update(Discount discount);

    long delete(Long id);

    Discount findById(Long id);

    Discount findByTitle(String title);

}
