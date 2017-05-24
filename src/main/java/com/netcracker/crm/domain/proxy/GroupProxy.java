package com.netcracker.crm.domain.proxy;

import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;

/**
 * @author Karpunets
 * @since 14.05.2017
 */
public class GroupProxy implements Group {
    private long id;
    private Group group;
    private GroupDao groupDao;

    public GroupProxy(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return getGroup().getName();
    }

    @Override
    public void setName(String name) {
        getGroup().setName(name);
    }

    @Override
    public Discount getDiscount() {
        return getGroup().getDiscount();
    }

    @Override
    public void setDiscount(Discount discount) {
        getGroup().setDiscount(discount);
    }

    private Group getGroup() {
        if (group == null) {
            group = groupDao.findById(id);
        }
        return group;
    }
}