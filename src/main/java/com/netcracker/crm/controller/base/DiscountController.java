package com.netcracker.crm.controller.base;

import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.dto.DiscountDto;
import com.netcracker.crm.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Pasha on 01.05.2017.
 */
@RestController
public class DiscountController {
    @Autowired
    private DiscountService discountService;


    @RequestMapping(value = "/csr/addDiscount", method = RequestMethod.POST)
    public String addProduct(DiscountDto discountDto){
        Discount disc = discountService.persist(discountDto);
        return "Discount with title : " + disc.getTitle() + " successful added";
    }

    @RequestMapping(value = "/csr/discountByTitle/{title}", method = RequestMethod.GET)
    public List<Discount> discountByTitle(@PathVariable String title){
        return discountService.getProductDiscount(title);
    }
}
