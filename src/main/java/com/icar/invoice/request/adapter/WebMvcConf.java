package com.icar.invoice.request.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {
    public static final String SESSION_KEY = "invoice_user";

    @Autowired
    private UserSecurityInterceptor securityInterceptor;

    public void  addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration addInterceptor = registry.addInterceptor(securityInterceptor);

        addInterceptor.excludePathPatterns("/data");
        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/user/logout");
        addInterceptor.excludePathPatterns("/api/user/login");
        addInterceptor.excludePathPatterns("/web/dependence/**");

        addInterceptor.addPathPatterns("/**");
    }
}