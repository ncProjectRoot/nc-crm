package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class GroupProxy extends Group {

    private Long discountId;

    private DiscountDao discountDao;

    public GroupProxy(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Override
    public Discount getDiscount() {
        if (super.getDiscount() == null && discountId != null) {
            super.setDiscount(discountDao.findById(discountId));
        }
        return super.getDiscount();
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }
}
