package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.ProductStatus;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductStatusDto;
import com.netcracker.crm.dto.mapper.DiscountMapper;
import com.netcracker.crm.dto.mapper.GroupMapper;
import com.netcracker.crm.dto.mapper.ProductMapper;
import com.netcracker.crm.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 30.04.2017.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;


    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product persist(ProductDto productDto){
        Product product = convertToEntity(productDto);
        productDao.create(product);
        return product;
    }

    @Override
    public List<ProductStatusDto> getStatuses() {
        List<ProductStatusDto> list = new ArrayList<>();
        for (ProductStatus status : ProductStatus.values()){
            list.add(new ProductStatusDto(status.getId(), status.getName()));
        }
        return list;
    }

    @Override
    public List<String> getNames(String likeTitle) {
        return productDao.findProductsTitleLikeTitle(likeTitle);
    }

    private Product convertToEntity(ProductDto productDto) {
        ModelMapper mapper = configureMapper();
        Discount discGroup = null;
        Group group = null;
        Discount discount = null;

        if (productDto.getGroup() != null) {
            if (productDto.getGroup().getDiscount() != null) {
                discGroup = mapper.map(productDto.getGroup().getDiscount(), Discount.class);
            }
            group = mapper.map(productDto.getGroup(), Group.class);
            group.setDiscount(discGroup);
        }
        if (productDto.getDiscount() != null) {
            discount = mapper.map(productDto.getDiscount(), Discount.class);
        }
        Product product = mapper.map(productDto, Product.class);

        product.setDiscount(discount);
        product.setGroup(group);

        return product;
    }


    private ModelMapper configureMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new ProductMapper());
        modelMapper.addMappings(new GroupMapper());
        modelMapper.addMappings(new DiscountMapper());

        return modelMapper;
    }
}
