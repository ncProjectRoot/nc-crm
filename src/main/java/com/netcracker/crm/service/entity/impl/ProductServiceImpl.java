package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.request.ProductRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;
import com.netcracker.crm.dto.bulk.ProductBulkDto;
import com.netcracker.crm.dto.mapper.ProductGroupDtoMapper;
import com.netcracker.crm.dto.mapper.ProductMapper;
import com.netcracker.crm.dto.row.ProductRowDto;
import com.netcracker.crm.listener.event.ChangeStatusProductEvent;
import com.netcracker.crm.listener.event.CreateProductEvent;
import com.netcracker.crm.service.entity.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Pasha on 30.04.2017.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;
    private final GroupDao groupDao;
    private final DiscountDao discountDao;
    private ApplicationEventPublisher publisher;


    @Autowired
    public ProductServiceImpl(ProductDao productDao, GroupDao groupDao, DiscountDao discountDao, ApplicationEventPublisher publisher) {
        this.productDao = productDao;
        this.groupDao = groupDao;
        this.discountDao = discountDao;
        this.publisher = publisher;
    }

    @Override
    public Product getProductsById(Long id) {
        return productDao.findById(id);
    }

    @Override
    @Transactional
    public Product create(ProductDto productDto, User user) {
        Product product = convertToEntity(productDto);
        product.setStatus(ProductStatus.PLANNED);
        productDao.create(product);
        publisher.publishEvent(new CreateProductEvent(this, product, user));
        return product;
    }

    @Override
    @Transactional
    public boolean update(ProductDto productDto, User user) {
        Product product = convertToEntity(productDto);
        Product productFromDB = productDao.findById(productDto.getId());
        product.setStatus(productFromDB.getStatus());
        return productDao.update(product) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutocompleteDto> getAutocompleteDto(String pattern) {
        List<Product> products = productDao.findAllByPattern(pattern);
        return convertToAutocompletesDto(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutocompleteDto> getAutocompleteDtoWithoutGroup(String pattern) {
        List<Product> products = productDao.findWithoutGroupByPattern(pattern);
        return convertToAutocompletesDto(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutocompleteDto> getActualProductsAutocompleteDtoByCustomer(String pattern, User customer) {
        List<Product> products = productDao.findActualByPatternAndCustomerId(pattern, customer.getId());
        return convertToAutocompletesDto(products);
    }

    @Override
    @Transactional
    public boolean bulkUpdate(ProductBulkDto bulkDto, User user) {
        Product productTemplate = getBulkProduct(bulkDto);
        Set<Long> productIDs = new HashSet<>();
        if (bulkDto.getItemIds() != null) productIDs.addAll(bulkDto.getItemIds());
        for (Long productID : productIDs) {
            if (productTemplate.getStatus() != null) changeStatus(productID, productTemplate.getStatus().getId(), user);
        }

        return productDao.bulkUpdate(productIDs, productTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutocompleteDto> getPossibleProductsAutocompleteDtoByCustomer(String pattern, User customer) {
        List<Product> products = productDao.findByPatternAndCustomerIdAndRegionId(pattern, customer.getId(), customer.getAddress().getRegion().getId());
        return convertToAutocompletesDto(products);
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

    private Product getBulkProduct(ProductBulkDto bulkDto) {
        Product productTemplate = new Product();
        if (bulkDto.isDescriptionChanged()) productTemplate.setDescription(bulkDto.getDescription());
        if (bulkDto.isStatusNameChanged()) productTemplate.setStatus(ProductStatus.valueOf(bulkDto.getStatusName()));
        if (bulkDto.isDefaultPriceChanged()) productTemplate.setDefaultPrice(bulkDto.getDefaultPrice());
        if (bulkDto.isGroupIdChanged()) {
            Group group = new Group();
            group.setId(bulkDto.getGroupId());
            productTemplate.setGroup(group);
        }
        if (bulkDto.isDiscountIdChanged()) {
            Discount discount = new Discount();
            discount.setId(bulkDto.getDiscountId());
            productTemplate.setDiscount(discount);
        }
        return productTemplate;
    }

    @Override
    public boolean hasCustomerAccessToProduct(Long productId, Long customerId) {
        return productDao.hasCustomerAccessToProduct(productId, customerId);
    }

    @Override
    @Transactional
    public boolean changeStatus(Long productId, Long statusId, User user) {
        if (statusId.equals(ProductStatus.ACTUAL.getId())) {
            return changeStatusToActual(productId, user);
        } else if (statusId.equals(ProductStatus.OUTDATED.getId())) {
            return changeStatusToOutdated(productId, user);
        }
        return false;
    }

    @Transactional
    private boolean changeStatusToOutdated(Long productId, User user) {
        Product product = productDao.findById(productId);
        ChangeStatusProductEvent event = new ChangeStatusProductEvent(this, product, user, ProductStatus.OUTDATED);
        publisher.publishEvent(event);
        return event.isDone();
    }

    @Transactional
    private boolean changeStatusToActual(Long productId, User user) {
        Product product = productDao.findById(productId);
        ChangeStatusProductEvent event = new ChangeStatusProductEvent(this, product, user, ProductStatus.ACTUAL);
        publisher.publishEvent(event);
        return event.isDone();
    }

    private List<AutocompleteDto> convertToAutocompletesDto(List<Product> products) {
        List<AutocompleteDto> result = new ArrayList<>();
        for (Product product : products) {
            AutocompleteDto autocompleteDto = new AutocompleteDto();
            autocompleteDto.setId(product.getId());
            autocompleteDto.setValue(product.getTitle());
            result.add(autocompleteDto);
        }
        return result;
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
            productRowDto.setDiscountActive(product.getDiscount().isActive());
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
