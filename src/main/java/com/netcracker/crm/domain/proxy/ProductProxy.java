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

    private Long discountId;
    private Long groupId;

    private DiscountDao discountDao;
    private GroupDao groupDao;

    public ProductProxy(DiscountDao discountDao, GroupDao groupDao) {
        this.discountDao = discountDao;
        this.groupDao = groupDao;
    }

    @Override
    public Discount getDiscount() {
        if (super.getDiscount() == null && discountId != null) {
            super.setDiscount(discountDao.findById(discountId));
        }
        return super.getDiscount();
    }

    @Override
    public Group getGroup() {
        if (super.getGroup() == null && groupId != null) {
            super.setGroup(groupDao.findById(groupId));
        }
        return super.getGroup();
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
