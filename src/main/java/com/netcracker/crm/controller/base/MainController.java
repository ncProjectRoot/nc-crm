package com.netcracker.crm.controller.base;

import java.util.Map;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Karpunets
 * @since 25.04.2017
 */

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Map<String, Object> model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetailsImpl) {
            user = (UserDetailsImpl) principal;
        } else {
            //!production
            user = new User();
            user.setFirstName("Tom");
            user.setLastName("Cat");
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                user.setUserRole(UserRole.valueOf(authority.getAuthority()));
            }
        }
        model.put("user", user);
        model.put("newMessage", 5);
        model.put("currentLanguage", "English");

        return "main";
    }

}