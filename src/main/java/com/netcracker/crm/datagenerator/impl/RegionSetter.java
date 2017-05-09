package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.RegionDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 06.05.2017.
 */
@Service
public class RegionSetter extends AbstractSetter<Region> {

    private int counter;
    @Autowired
    private RegionDao regionDao;
    @Override
    public List<Region> generate(int numbers) {
        List<Region> regions = new ArrayList<>();

        for (int i = 0; i < numbers; i++) {
            Region region = generateObject();
            regionDao.create(region);
            regions.add(region);
        }
        return regions;
    }

    @Override
    public Region generateObject() {
        Region region = new Region();
        region.setName("Region " + counter++);
        return region;
    }
}
