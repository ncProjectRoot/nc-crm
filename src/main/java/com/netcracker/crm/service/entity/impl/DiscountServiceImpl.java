package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.request.DiscountRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.DiscountDto;
import com.netcracker.crm.dto.bulk.DiscountBulkDto;
import com.netcracker.crm.dto.mapper.DiscountMapper;
import com.netcracker.crm.dto.row.DiscountRowDto;
import com.netcracker.crm.service.entity.DiscountService;
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
public class DiscountServiceImpl implements DiscountService {
    private static final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountDao discountDao;

    @Autowired
    public DiscountServiceImpl(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Override
    @Transactional
    public Discount create(DiscountDto discountDto) {
        Discount discount = convertToEntity(discountDto);
        discountDao.create(discount);
        return discount;
    }

    @Override
    @Transactional
    public boolean update(DiscountDto discountDto) {
        Discount discount = convertToEntity(discountDto);
        Long updateId = discountDao.update(discount);
        return updateId > 0;
    }

    @Override
    public Discount getDiscountById(Long id) {
        return discountDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutocompleteDto> getAutocompleteDto(String pattern) {
        List<Discount> discounts = discountDao.findByIdOrTitle(pattern);
        List<AutocompleteDto> result = new ArrayList<>();
        for (Discount discount : discounts) {
            result.add(convertToAutocompleteDto(discount));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getDiscountRows(DiscountRowRequest rowRequest) {
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
    @Transactional
    public boolean bulkUpdate(DiscountBulkDto bulkDto) {
        Discount discountTemplate = getBulkDiscount(bulkDto);
        Set<Long> discountIDs = new HashSet<>();
        if (bulkDto.getItemIds() != null) discountIDs.addAll(bulkDto.getItemIds());

        return discountDao.bulkUpdate(discountIDs, discountTemplate);
    }

    private Discount getBulkDiscount(DiscountBulkDto bulkDto) {
        Discount discountTemplate = new Discount();
        if (bulkDto.isDescriptionChanged()) discountTemplate.setDescription(bulkDto.getDescription());
        if (bulkDto.isActiveChanged()) {
            boolean isActive = bulkDto.isActive() == null ? false : bulkDto.isActive();
            discountTemplate.setActive(isActive);
        }
        if (bulkDto.isPercentageChanged()) discountTemplate.setPercentage(bulkDto.getPercentage());

        return discountTemplate;
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

    private AutocompleteDto convertToAutocompleteDto(Discount discount) {
        AutocompleteDto autocompleteDto = new AutocompleteDto();
        autocompleteDto.setId(discount.getId());
        autocompleteDto.setValue(discount.getTitle());
        return autocompleteDto;
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
