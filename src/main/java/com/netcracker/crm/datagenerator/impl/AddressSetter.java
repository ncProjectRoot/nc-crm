package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Region;
import com.netcracker.crm.service.security.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Pasha on 06.05.2017.
 */
@Service
public class AddressSetter extends AbstractSetter<Address> {

    @Autowired
    private AddressDao addressDao;
    private List<Region> regions;
    private RandomString randomString1 = new RandomString(10);

    @Override
    public List<Address> generate(int numbers) {
        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            Address address= generateObject();
            addressDao.create(address);
            addresses.add(address);
        }
        return addresses;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @Override
    public Address generateObject() {
        Address address = new Address();
        address.setDetails(randomString.nextString());
        address.setLatitude((double)Math.round(Math.random() * 1000_000));
        address.setLongitude((double)Math.round(Math.random() * 1000_000));
        address.setFormattedAddress(randomString1.nextString());
        address.setRegion(getRegion());
        return address;
    }


    private Region getRegion(){
        return regions.get(random.nextInt(regions.size()));
    }
}
