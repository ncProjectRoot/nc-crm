package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.ProductStatus;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductStatusDto;
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
    private GroupDao groupDao;
    @Autowired
    private DiscountDao discountDao;


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

    private Product convertToEntity(ProductDto productDto) {
        ModelMapper mapper = configureMapper();

        System.out.println(productDto);
        Group group = productDto.getGroupId() > 0 ? groupDao.findById(productDto.getGroupId()) : null;
        Discount discount = productDto.getDiscountId() > 0 ? discountDao.findById(productDto.getDiscountId()) : null;

        Product product = mapper.map(productDto, Product.class);

        product.setDiscount(discount);
        product.setGroup(group);

        return product;
    }


    private ModelMapper configureMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new ProductMapper());

        return modelMapper;
    }
}
