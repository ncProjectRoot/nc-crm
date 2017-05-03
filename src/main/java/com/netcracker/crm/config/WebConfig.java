package com.netcracker.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by bpogo on 5/2/2017.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    public static final String PATH_USER_REGISTRATION = "/user/registration";
    private final HandlerInterceptor createUserInterceptor;

    @Autowired
    public WebConfig(@Qualifier("createUserInterceptor") HandlerInterceptor createUserInterceptor) {
        this.createUserInterceptor = createUserInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createUserInterceptor)
                .addPathPatterns(PATH_USER_REGISTRATION);
    }
}
