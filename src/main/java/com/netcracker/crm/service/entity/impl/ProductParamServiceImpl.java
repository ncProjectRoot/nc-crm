package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.ProductParamDao;
import com.netcracker.crm.domain.model.ProductParam;
import com.netcracker.crm.domain.real.RealProductParam;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.ProductParamDto;
import com.netcracker.crm.dto.mapper.ModelMapper;
import com.netcracker.crm.dto.mapper.impl.ProductParamMapper;
import com.netcracker.crm.service.entity.ProductParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YARUS
 */
@Service
public class ProductParamServiceImpl implements ProductParamService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductParamDao productParamDao;
    private final ProductParamMapper productParamMapper;

    @Autowired
    public ProductParamServiceImpl(ProductParamDao productParamDao, ProductParamMapper productParamMapper) {
        this.productParamDao = productParamDao;
        this.productParamMapper = productParamMapper;
    }

    @Override
    @Transactional
    public ProductParam create(ProductParamDto productParamDto) {
        ProductParam productParam = ModelMapper.map(productParamMapper.dtoToModel(), productParamDto, RealProductParam.class);
        productParamDao.create(productParam);
        return productParam;
    }

    @Override
    @Transactional
    public boolean update(ProductParamDto productParamDto) {
        ProductParam productParam = ModelMapper.map(productParamMapper.dtoToModel(), productParamDto, RealProductParam.class);
        return productParamDao.update(productParam) > 0;
    }


    @Override
    @Transactional
    public boolean delete(Long id) {
        return productParamDao.delete(id) > 0;
    }

    @Override
    public List<ProductParam> getAllByProductId(Long id) {
        return productParamDao.findAllByProductId(id);
    }

    @Override
    public List<ProductParam> getAllByParamName(String paramName) {
        return productParamDao.findAllByParamName(paramName);
    }

    @Override
    public ProductParam getById(Long id) {
        return productParamDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutocompleteDto> getAutocompleteDto(String pattern) {
        List<ProductParam> params = productParamDao.findByIdOrTitle(pattern);
        return ModelMapper.mapList(productParamMapper.modelToAutocomplete(), params, AutocompleteDto.class);
    }

}
