package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 05.05.2017.
 */
@Service
public class GroupSetter extends AbstractSetter<Group> {

    @Autowired
    private GroupDao groupDao;
    private int counter;
    private List<Discount> discounts;

    @Override
    public List<Group> generate(int numbers) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            Group group = generateObject();
            groupDao.create(group);
            groups.add(group);
        }
        return groups;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public Group generateObject() {
        Group group = new Group();
        group.setName("Group" + counter++);
        group.setDiscount(getDiscount());
        return group;
    }


    private Discount getDiscount(){
        return Math.random() > 0.5 ? discounts.remove(random.nextInt(discounts.size())) : null;
    }
}
