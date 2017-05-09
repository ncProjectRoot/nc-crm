package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.request.ProductRowRequest;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;
import com.netcracker.crm.dto.mapper.ProductGroupDtoMapper;
import com.netcracker.crm.dto.mapper.ProductMapper;
import com.netcracker.crm.dto.row.ProductRowDto;
import com.netcracker.crm.service.entity.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 30.04.2017.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;
    private final GroupDao groupDao;
    private final DiscountDao discountDao;


    @Autowired
    public ProductServiceImpl(ProductDao productDao, GroupDao groupDao, DiscountDao discountDao) {
        this.productDao = productDao;
        this.groupDao = groupDao;
        this.discountDao = discountDao;
    }

    @Override
    public Product getProductsById(Long id) {
        return productDao.findById(id);
    }

    @Override
    @Transactional
    public Product persist(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        productDao.create(product);
        return product;
    }

    @Override
    public Product update(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        productDao.update(product);
        return product;
    }

    @Override
    public List<ProductGroupDto> getProductsWithoutGroup() {
        List<Product> products = productDao.findAllWithoutGroup();
        return convertToDto(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getTitlesLikeTitle(String likeTitle) {
        return productDao.findProductsTitleLikeTitle(likeTitle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getNamesByCustomerId(String likeTitle, Long customerId) {
        return productDao.findProductsTitleByCustomerId(likeTitle, customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getActualNamesByCustomerId(String likeTitle, Long customerId) {
        return productDao.findActualProductsTitleByCustomerId(likeTitle, customerId);
    }

    @Override
    public boolean hasCustomerAccessToProduct(Long productId, Long customerId) {
        return productDao.hasCustomerAccessToProduct(productId, customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getActualNamesByCustomerId(String likeTitle, Long customerId, Address address) {
        if (address.getRegion() != null) {
            return productDao.findActualProductsTitleByCustomerId(likeTitle, customerId, address.getRegion().getId());
        }
        return productDao.findActualProductsTitleByCustomerId(likeTitle, customerId, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getProductsRow(ProductRowRequest orderRowRequest) {
        Map<String, Object> response = new HashMap<>();
        Long length = productDao.getProductRowsCount(orderRowRequest);
        response.put("length", length);
        List<Product> products = productDao.findProductRows(orderRowRequest);

        List<ProductRowDto> productsRowDto = new ArrayList<>();
        for (Product product : products) {
            productsRowDto.add(convertToRowDto(product));
        }
        response.put("rows", productsRowDto);
        return response;
    }

    private ProductRowDto convertToRowDto(Product product) {
        ProductRowDto productRowDto = new ProductRowDto();
        productRowDto.setId(product.getId());
        productRowDto.setTitle(product.getTitle());
        productRowDto.setPrice(product.getDefaultPrice());
        productRowDto.setStatus(product.getStatus().getName());
        if (product.getDiscount() != null) {
            productRowDto.setDiscount(product.getDiscount().getId());
            productRowDto.setDiscountTitle(product.getDiscount().getTitle());
            productRowDto.setPercentage(product.getDiscount().getPercentage());
            productRowDto.setDiscountActive(product.getDiscount().getActive());
        }
        if (product.getGroup() != null) {
            productRowDto.setGroup(product.getGroup().getId());
            productRowDto.setGroupName(product.getGroup().getName());
        }
        return productRowDto;
    }

    private Product convertToEntity(ProductDto productDto) {
        ModelMapper mapper = configureMapper();

        Group group = productDto.getGroupId() > 0 ? groupDao.findById(productDto.getGroupId()) : null;
        Discount discount = productDto.getDiscountId() > 0 ? discountDao.findById(productDto.getDiscountId()) : null;

        Product product = mapper.map(productDto, Product.class);

        product.setDiscount(discount);
        product.setGroup(group);

        return product;
    }


    private List<ProductGroupDto> convertToDto(List<Product> products) {
        ModelMapper mapper = configureMapper();
        List<ProductGroupDto> result = new ArrayList<>();
        for (Product product : products) {
            result.add(mapper.map(product, ProductGroupDto.class));
        }
        return result;
    }

    private ModelMapper configureMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new ProductMapper());
        modelMapper.addMappings(new ProductGroupDtoMapper());

        return modelMapper;
    }
}
