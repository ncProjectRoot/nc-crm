package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.RegionGroupsDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 06.05.2017.
 */
@Service
public class RegionGroupsSetter extends AbstractSetter {
    private List<Region> regions;
    private List<Group> groups;

    @Autowired
    private RegionGroupsDao regionGroupsDao;

    @Override
    public List generate(int numbers) {

        for (Region region : regions){
            for (Group group : getRegionGroups(groups)){
                regionGroupsDao.create(region, group);
            }
        }
        return new ArrayList();
    }


    private List<Group> getRegionGroups(List<Group> groups){
        List<Group> groupList = new ArrayList<>();
        for (Group group : groups){
            if (Math.random() > 0.4){
                groupList.add(group);
            }
        }
        return groupList;
    }

    @Override
    public Object generateObject() {
        return null;
    }


    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
