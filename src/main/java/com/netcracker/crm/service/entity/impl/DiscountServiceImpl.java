package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.request.DiscountRowRequest;
import com.netcracker.crm.dto.DiscountDto;
import com.netcracker.crm.dto.mapper.DiscountMapper;
import com.netcracker.crm.dto.row.DiscountRowDto;
import com.netcracker.crm.service.entity.DiscountService;
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
 * Created by Pasha on 01.05.2017.
 */
@Service
public class DiscountServiceImpl implements DiscountService {
    private static final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountDao discountDao;

    @Autowired
    public DiscountServiceImpl(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }


    @Override
    @Transactional
    public Discount persist(DiscountDto discountDto) {
        Discount discount = convertToEntity(discountDto);
        discountDao.create(discount);
        return discount;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getDiscounts(DiscountRowRequest rowRequest) {
        Map<String, Object> response = new HashMap<>();
        Long length = discountDao.getDiscountRowsCount(rowRequest);
        response.put("length", length);

        List<Discount> discounts = discountDao.findDiscounts(rowRequest);
        List<DiscountRowDto> dtoRows = new ArrayList<>();
        for (Discount discount : discounts) {
            dtoRows.add(convertToRowDto(discount));
        }
        response.put("rows", dtoRows);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discount> getDiscountByTitle(String title) {
        return discountDao.findByTitle(title);
    }

    private DiscountRowDto convertToRowDto(Discount discount) {
        DiscountRowDto rowDto = new DiscountRowDto();
        rowDto.setId(discount.getId());
        rowDto.setTitle(discount.getTitle());
        rowDto.setPercentage(discount.getPercentage());
        rowDto.setDiscountActive(discount.isActive());
        rowDto.setDescription(discount.getDescription());

        return rowDto;
    }

    private Discount convertToEntity(DiscountDto discountDto) {
        ModelMapper mapper = configureMapper();
        Discount discount = mapper.map(discountDto, Discount.class);
        if (discountDto.getActive() == null) {
            discount.setActive(false);
        }
        return discount;
    }


    private ModelMapper configureMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new DiscountMapper());

        return modelMapper;
    }
}
