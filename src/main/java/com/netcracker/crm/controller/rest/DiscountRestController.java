package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.domain.request.DiscountRowRequest;
import com.netcracker.crm.dto.DiscountDto;
import com.netcracker.crm.service.entity.DiscountService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.DiscountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.controller.message.MessageHeader.ERROR_MESSAGE;
import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_SERVER_ERROR;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_DISCOUNT_CREATED;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_DISCOUNT_UPDATED;

/**
 * Created by Pasha on 01.05.2017.
 */
@RestController
@RequestMapping(value = "/discounts")
public class DiscountRestController {
    private final DiscountService discountService;
    private final DiscountValidator discountValidator;
    private final ResponseGenerator<Discount> generator;
    private final BindingResultHandler bindingResultHandler;

    @Autowired
    public DiscountRestController(DiscountService discountService, DiscountValidator discountValidator,
                                  ResponseGenerator<Discount> generator, BindingResultHandler bindingResultHandler) {
        this.discountService = discountService;
        this.discountValidator = discountValidator;
        this.generator = generator;
        this.bindingResultHandler = bindingResultHandler;
    }


    @PostMapping
    public ResponseEntity<?> createDiscount(@Valid DiscountDto discountDto, BindingResult bindingResult) {
        discountValidator.validate(discountDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResultHandler.handle(bindingResult);
        }
        Discount disc = discountService.persist(discountDto);
        if (disc.getId() > 0) {
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_DISCOUNT_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<?> updateDiscount(@Valid DiscountDto discountDto, BindingResult bindingResult) {
        discountValidator.validate(discountDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResultHandler.handle(bindingResult);
        }
        boolean isUpdated = discountService.updateDiscount(discountDto);
        if (isUpdated) {
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_DISCOUNT_UPDATED, HttpStatus.OK);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/csr/discountByTitle/", method = RequestMethod.GET)
    public List<AutocompleteDto> discountByPattern(String pattern) {
        return discountService.getAutocompleteDiscount(pattern);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDiscounts(DiscountRowRequest rowRequest) {
        return new ResponseEntity<>(discountService.getDiscounts(rowRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/{title}")
    public ResponseEntity<List<Discount>> getDiscountByTitle(@PathVariable String title) {
        return new ResponseEntity<>(discountService.getDiscountByTitle(title), HttpStatus.OK);
    }
}
