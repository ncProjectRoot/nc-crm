package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Product;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class ProductProxy extends Product {

    private long discountId;
    private long groupId;

    private DiscountDao discountDao;
    private GroupDao groupDao;

    public ProductProxy(DiscountDao discountDao, GroupDao groupDao) {
        this.discountDao = discountDao;
        this.groupDao = groupDao;
    }

    @Override
    public Discount getDiscount() {
        if (super.getDiscount() == null && discountId != 0) {
            super.setDiscount(discountDao.findById(discountId));
        }
        return super.getDiscount();
    }

    @Override
    public Group getGroup() {
        if (super.getGroup() == null && groupId != 0) {
            super.setGroup(groupDao.findById(groupId));
        }
        return super.getGroup();
    }

    public long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
