package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.ProductParam;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.ProductParamDto;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface ProductParamService {
    
    ProductParam create(ProductParamDto productParamDto);
        
    boolean update(ProductParamDto productParamDto);
    
    boolean delete(Long id);
    
    List<ProductParam> getAllByProductId(Long id);

    List<AutocompleteDto> getAutocompleteDto(String pattern);
    
    List<ProductParam> getAllByParamName(String paramName);
    
    ProductParam getById(Long id);
}
