package com.netcracker.crm.controller.base;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.exception.NoSuchEmailException;
import com.netcracker.crm.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;

/**
 * Created by Pasha on 26.04.2017.
 */
@Controller
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public String checkEmailAndPhone(String email, String phone, Model model){
        try{
            User user = forgotPasswordService.checkEmailAndPhone(email, phone);
            forgotPasswordService.changePassword(user);
            model.addAttribute("msg", "You are successful recovery password");
        }catch (NoSuchEmailException e){
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "login";
    }
}
