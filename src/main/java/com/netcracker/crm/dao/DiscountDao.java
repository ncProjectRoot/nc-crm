package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Discount;

import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */
public interface DiscountDao {
    long create(Discount discount);

    long update(Discount discount);

    long delete(Long id);

    long delete(Discount discount);

    Discount findById(Long id);

    List<Discount> findByTitle(String title);

}
