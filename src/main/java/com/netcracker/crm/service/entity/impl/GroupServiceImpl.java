package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.request.GroupRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.GroupDto;
import com.netcracker.crm.dto.GroupTableDto;
import com.netcracker.crm.dto.mapper.GroupMapper;
import com.netcracker.crm.service.entity.GroupService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Pasha on 01.05.2017.
 */
@Service
public class GroupServiceImpl implements GroupService{
    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupDao groupDao;
    private final DiscountDao discountDao;
    private final ProductDao productDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, DiscountDao discountDao,ProductDao productDao) {
        this.groupDao = groupDao;
        this.discountDao = discountDao;
        this.productDao = productDao;
    }


    @Override
    @Transactional
    public Group create(GroupDto groupDto) {
        System.out.println(groupDto.getDiscountId());
        Group group = convertToEntity(groupDto);
        groupDao.create(group);
        if (groupDto.getProducts() != null) {
            List<Product> productList = new ArrayList<>();
            for (Long prodId : groupDto.getProducts()) {
                Product product = productDao.findById(prodId);
                product.setGroup(group);
                productList.add(product);
            }
            for (Product product : productList) {
                productDao.update(product);
            }
        }

        return group;
    }


    @Override
    @Transactional
    public boolean update(GroupDto groupDto) {
        Group group = groupDao.findById(groupDto.getId());
        group.setName(groupDto.getName());
        setGroupDiscount(group, groupDto.getDiscountId());
        List<Product> products = productDao.findAllByGroupId(group.getId());
        for (Long id : groupDto.getProducts()){
            Product product = productDao.findById(id);
            if (products.contains(product)){
                products.remove(product);
            }else {
                product.setGroup(group);
                if (productDao.update(product) <= 0){
                    return false;
                }

            }
        }
        for (Product product : products){
            product.getGroup().setId(null);
            productDao.update(product);
        }
        return groupDao.update(group) > 0;
    }

    private void setGroupDiscount(Group group, Long discountId){
        if (discountId == 0){
            group.setDiscount(null);
        }else if (!group.getId().equals(discountId)){
            group.setDiscount(discountDao.findById(discountId));
        }
    }

    @Override
    public Map<String, Object> getGroupPage(GroupRowRequest request) {
        Map<String, Object> result = new HashMap<>();
        Long length = groupDao.getCount(request);
        List<GroupTableDto> groups = groupDao.getPartRows(request);
        result.put("length", length);
        result.put("rows", groups);
        return result;
    }

    @Override
    public List<AutocompleteDto> getAutocompleteGroup(String pattern) {
        List<Group> groups = groupDao.findByIdOrTitle(pattern);
        List<AutocompleteDto> result = new ArrayList<>();
        for (Group group: groups) {
            result.add(convertToAutocompleteDto(group));
        }
        return result;
    }

    @Override
    public Group getGroupById(Long id) {
        return groupDao.findById(id);
    }

    private AutocompleteDto convertToAutocompleteDto(Group group) {
        AutocompleteDto autocompleteDto = new AutocompleteDto();
        autocompleteDto.setId(group.getId());
        autocompleteDto.setValue(group.getName());
        return autocompleteDto;
    }

    private Group convertToEntity(GroupDto groupDto) {
        ModelMapper mapper = configureMapper();

        Discount discount = groupDto.getDiscountId() > 0 ? discountDao.findById(groupDto.getDiscountId()) : null;
        Group group = mapper.map(groupDto, Group.class);

        group.setDiscount(discount);

        return group;
    }

    private ModelMapper configureMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new GroupMapper());

        return modelMapper;
    }
}
