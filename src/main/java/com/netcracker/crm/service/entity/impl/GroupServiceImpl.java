package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.dao.GroupDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.RegionGroupsDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.real.RealGroup;
import com.netcracker.crm.domain.request.GroupRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.GroupDto;
import com.netcracker.crm.dto.GroupTableDto;
import com.netcracker.crm.dto.mapper.ModelMapper;
import com.netcracker.crm.dto.mapper.impl.GroupMapper;
import com.netcracker.crm.service.entity.GroupService;
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
 * Created by Pasha on 01.05.2017.
 */
@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupDao groupDao;
    private final DiscountDao discountDao;
    private final ProductDao productDao;
    private final RegionGroupsDao regionGroupsDao;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, DiscountDao discountDao, ProductDao productDao,
                            RegionGroupsDao regionGroupsDao, GroupMapper groupMapper) {
        this.groupDao = groupDao;
        this.discountDao = discountDao;
        this.productDao = productDao;
        this.regionGroupsDao = regionGroupsDao;
        this.groupMapper = groupMapper;
    }

    @Override
    @Transactional
    public Group create(GroupDto groupDto) {
        Group group = ModelMapper.map(groupMapper.dtoToModel(), groupDto, RealGroup.class);
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
        for (Long id : groupDto.getProducts()) {
            Product product = productDao.findById(id);
            if (products.contains(product)) {
                products.remove(product);
            } else {
                product.setGroup(group);
                if (productDao.update(product) <= 0) {
                    return false;
                }

            }
        }
        for (Product product : products) {
            product.setGroup(null);
            productDao.update(product);
        }
        return groupDao.update(group) > 0;
    }

    private void setGroupDiscount(Group group, Long discountId) {
        if (discountId == 0) {
            group.setDiscount(null);
        } else if (!group.getId().equals(discountId)) {
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
        return ModelMapper.mapList(groupMapper.modelToAutocomplete(), groups, AutocompleteDto.class);
    }

    @Override
    public Group getGroupById(Long id) {
        return groupDao.findById(id);
    }

    @Override
    public List<Group> getGroupsByRegion(Region region) {
        return regionGroupsDao.findGroupsByRegion(region);
    }

    @Override
    public List<Group> getGroupsByDiscountId(Long id, User user) {
        List<Group> groups = new ArrayList<>();
        UserRole role = user.getUserRole();
        if (role.equals(UserRole.ROLE_ADMIN) || role.equals(UserRole.ROLE_CSR ) || role.equals(UserRole.ROLE_PMG)) {
            groups = groupDao.findByDiscountId(id);
        } else if (role.equals(UserRole.ROLE_CUSTOMER)) {
            groups = groupDao.findByDiscountIdAndCustomerId(id, user.getId());
        }
        return groups;
    }

}
