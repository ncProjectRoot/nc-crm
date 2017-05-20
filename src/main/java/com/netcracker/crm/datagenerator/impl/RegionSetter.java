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

    @Autowired
    private RegionDao regionDao;
    private String[] regionsName = {
                    "ARC" ,
                    "Vinnytsia" ,
                    "Volyn" ,
                    "Dnipropetrovsk" ,
                    "Donetsk" ,
                    "Zhytomyr" ,
                    "Zakarpattya" ,
                    "Zaporizhia" ,
                    "Ivano-Frankivsk" ,
                    "Kiev" ,
                    "Kirovohrad" ,
                    "Lugansk" ,
                    "Lviv" ,
                    "Mykolaiv" ,
                    "Odessa" ,
                    "Poltava" ,
                    "Rivne" ,
                    "Sums" ,
                    "Ternopil" ,
                    "Kharkov" ,
                    "Herson" ,
                    "Khmelnytsky" ,
                    "Cherkassy" ,
                    "Chernivtsi" ,
                    "Chernihiv" ,
                    "Sevastopol"
    };

    @Override
    public List<Region> generate(int numbers) {
        List<Region> regions = new ArrayList<>();

        for (String aRegionsName : regionsName) {
            Region region = generateObject();
            region.setName(aRegionsName);
            regionDao.create(region);
            regions.add(region);
        }
        return regions;
    }

    @Override
    public Region generateObject() {
        return new Region();
    }
}
