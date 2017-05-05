package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 05.05.2017.
 */
@Service
public class DiscountSetter extends AbstractSetter<Discount> {

    @Autowired
    private DiscountDao discountDao;
    private int counter;



    public List<Discount> generate(int numbers){
        List<Discount> idList = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            Discount discount = generateObject();
            discountDao.create(discount);
            idList.add(discount);
        }
        return idList;
    }



    public Discount generateObject(){
        Discount discount = new Discount();
        discount.setTitle("discount " + counter++);
        discount.setPercentage((double)Math.round(Math.random()* 100));
        discount.setDescription(randomString.nextString());
        discount.setActive(random.nextBoolean());
        return discount;
    }


}
