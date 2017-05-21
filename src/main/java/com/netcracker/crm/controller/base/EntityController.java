package com.netcracker.crm.controller.base;

import com.itextpdf.text.DocumentException;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.pdf.PDFGenerator;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Karpunets
 * @since 07.05.2017
 */

@Controller
public class EntityController {
    private final ComplaintService complaintService;
    private final ProductService productService;
    private final OrderService orderService;
    private final DiscountService discountService;
    private final GroupService groupService;
    private final UserService userService;


    @Autowired
    public EntityController(ComplaintService complaintService, ProductService productService,
                            OrderService orderService, DiscountService discountService, GroupService groupService, UserService userService) {
        this.complaintService = complaintService;
        this.productService = productService;
        this.orderService = orderService;
        this.discountService = discountService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/*/complaint/{id}")
    public String complaint(Map<String, Object> model, @PathVariable("id") Long id,
                            Authentication authentication) {
        User customer = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
        }
        boolean allowed = complaintService.checkAccessToComplaint(customer, id);
        if (allowed) {
            Complaint complaint = complaintService.findById(id);
            model.put("complaint", complaint);
            return "complaint";
        } else {
            return "403";
        }
    }

    @GetMapping("/*/product/{id}")
    public String product(Map<String, Object> model, Authentication authentication, @PathVariable("id") Long id) {
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

    @GetMapping("/*/order/{id}")
    public String order(Map<String, Object> model, Authentication authentication, @PathVariable("id") Long id) {
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

    @RequestMapping("/*/discount/{id}")
    public String discount(Map<String, Object> model, Authentication authentication, @PathVariable("id") Long id) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        }
        model.put("discount", discountService.getDiscountById(id));
        return "discount";
    }


    @RequestMapping("/*/group/{id}")
    public String group(Map<String, Object> model, @PathVariable Long id) {
        model.put("group", groupService.getGroupById(id));
        model.put("products", productService.getProductsByGroupId(id));
        return "group";
    }

    @RequestMapping(value = "/{role}/user/{id}", method = {RequestMethod.GET})
    public String user(Map<String, Object> model, Authentication authentication,
                       @PathVariable Long id) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        }
        model.put("user", userService.getUserById(id));
        return "user";
    }

    @RequestMapping(value = "/{role}/profile", method = {RequestMethod.GET})
    public String profile(Map<String, Object> model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        }

        User user1 = (User) authentication.getPrincipal();
        long id = user1.getId();
        model.put("profile", userService.getUserById(id));
        return "profile";
    }



    @RequestMapping(path = "/{role}/order/{id}/report", method = RequestMethod.GET)
    public String report(Map<String, Object> model, Authentication authentication, @PathVariable("id") Long id, produces) throws DocumentException, MessagingException, IOException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException {

        Object principal = authentication.getPrincipal();
//        User user;
//        if (principal instanceof UserDetailsImpl) {
//            user = (UserDetailsImpl) principal;
//        }

        PDFGenerator pdfGenerator = new PDFGenerator();
        Order order = orderService.getOrderById(id);
        User user = order.getCustomer();
        Product product = order.getProduct();
        Discount discount = order.getProduct().getDiscount();

        pdfGenerator.generate(order, user, product, discount);
        response.setContentType("application/pdf");
        //response.setContentLength(content.length);
        response.setHeader("Content-Disposition", "inline; filename=" + pdfGenerator.generateFileName());
        model.put("pdf", pdfGenerator.generateFileName());

        return pdfGenerator.generate(order, user, product, discount);
    }

//    @RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
//    @ResponseBody
//    public FileSystemResource getFile(@PathVariable("file_name") String fileName) {
//        return new FileSystemResource();
//    }
}