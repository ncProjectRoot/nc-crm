package com.netcracker.crm.exception.handler;

import com.netcracker.crm.exception.NoSuchEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Pasha on 30.04.2017.
 */

@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    @ExceptionHandler({BadCredentialsException.class, LockedException.class})
    public ModelAndView handleError(RuntimeException exception) {
        ModelAndView mav = new ModelAndView();
        if (exception instanceof BadCredentialsException){
            mav.addObject("error", "Invalid email or password");
        }else if (exception instanceof LockedException){
            mav.addObject("error", exception.getMessage());
        }

        mav.setViewName("login");
        return mav;
    }

    @ExceptionHandler({NoSuchEmailException.class, MessagingException.class})
    public ModelAndView handleNoSuchEmail(Exception exception){
        ModelAndView mav = new ModelAndView();
        if (exception instanceof NoSuchEmailException) {
            mav.addObject("error", exception.getMessage());
        }else if (exception instanceof MessagingException){
            mav.addObject("error", "Something error with send email");
        }
        mav.setViewName("login");
        return mav;
    }

}
