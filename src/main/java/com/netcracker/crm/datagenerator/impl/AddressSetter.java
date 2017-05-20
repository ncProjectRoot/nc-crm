package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.AddressDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Region;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 06.05.2017.
 */
@Service
public class AddressSetter extends AbstractSetter<Address> {

    @Autowired
    private AddressDao addressDao;
    private List<Region> regions;
    private List<String> addresses = new ArrayList<>();
    @Value(value = "classpath:testdata/address.json")
    private Resource resource;
    @Override
    public List<Address> generate(int numbers) {
        List<Address> addresses = new ArrayList<>();
        fillAddress();
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

    private void fillAddress(){
        JSONArray a = null;
        try {
            a = (JSONArray) parser.parse(resource.getInputStream());
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        for (Object o : a) {
            JSONObject person = (JSONObject) o;
            String address = (String) person.get("address");
            addresses.add(address);
        }
    }

    @Override
    public Address generateObject() {
        Address address = new Address();
        String addr= addresses.get(random.nextInt(addresses.size()));
        address.setDetails(addr);
        address.setLatitude((double)Math.round(Math.random() * 1000_000));
        address.setLongitude((double)Math.round(Math.random() * 1000_000));
        address.setFormattedAddress(addr);
        address.setRegion(getRegion());
        return address;
    }


    private Region getRegion(){
        return regions.get(random.nextInt(regions.size()));
    }
}
