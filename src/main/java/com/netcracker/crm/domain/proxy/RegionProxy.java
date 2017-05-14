package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Region;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class RegionProxy extends Region {

    private long discountId;

    private DiscountDao discountDao;

    public RegionProxy(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Override
    public Discount getDiscount() {
        if (super.getDiscount() == null && discountId != 0) {
            super.setDiscount(discountDao.findById(discountId));
        }
        return super.getDiscount();
    }

    public long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }
}
