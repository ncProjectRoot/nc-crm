package com.netcracker.crm.controller.base;

import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Pasha on 02.05.2017.
 */
@Controller
public class RegisterController {

    @Autowired
    private UserTokenService userTokenService;

    @RequestMapping(value = "/user/confirm/registration", method = RequestMethod.GET)
    public String confirmRegistration(Long userId, String token, Model model){
        UserToken userToken = userTokenService.getExistUserToken(userId, token);
        if (userTokenService.useToken(userToken)){
            model.addAttribute("msg", "You are successful activate you account!");
            return "login";
        }
        return "error";
    }
}
