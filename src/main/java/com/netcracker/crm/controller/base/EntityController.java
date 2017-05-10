package com.netcracker.crm.controller.base;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.entity.OrderService;
import com.netcracker.crm.service.entity.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Karpunets
 * @since 07.05.2017
 */

@Controller
public class EntityController {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/*/product", method = {RequestMethod.GET})
    public String product(Map<String, Object> model, Authentication authentication,
                          @RequestParam Long id) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
            if (user.getUserRole() == UserRole.ROLE_CUSTOMER) {
                if (!productService.hasCustomerAccessToProduct(id, user.getId())) {
                    return "error/403"; //TODO: error/403.jsp
                }
                model.put("hasProduct", orderService.hasCustomerProduct(id, user.getId()));
            }
        }
        model.put("product", productService.getProductsById(id));
        return "product";
    }

    @RequestMapping(value = "/*/order", method = {RequestMethod.GET})
    public String order(Map<String, Object> model, Authentication authentication,
                        @RequestParam Long id) {
        Object principal = authentication.getPrincipal();
        Order order = orderService.getOrderById(id);
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
            if (user.getUserRole() == UserRole.ROLE_CUSTOMER && !order.getCustomer().getId().equals(user.getId())) {
                return "error/403";
            }
        }
        model.put("order", order);
        return "order";
    }

}