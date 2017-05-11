package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 05.05.2017.
 */

@Service
public class ProductSetter extends AbstractSetter<Product> {

    private int defaultPrice = 10_000;

    @Autowired
    private ProductDao productDao;
    private List<Discount> discounts;
    private List<Group> groups;
    private int counter;
    @Override
    public List<Product> generate(int numbers) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            Product product = generateObject();
            productDao.create(product);
            products.add(product);
        }
        return products;
    }

    @Override
    public Product generateObject() {
        Product product = new Product();
        product.setDiscount(getDiscount());
        product.setGroup(getGroup());
        product.setDefaultPrice((double) random.nextInt(defaultPrice));
        product.setTitle("Product " + counter++);
        product.setDescription(randomString.nextString());
        product.setStatus(ProductStatus.values()[random.nextInt(ProductStatus.values().length)]);
        return product;
    }


    private Group getGroup(){
        return Math.random() < 0.8 ? groups.get(random.nextInt(groups.size())) : null;
    }

    private Discount getDiscount(){
        return Math.random() > 0.5 ? discounts.remove(random.nextInt(discounts.size())) : null;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
