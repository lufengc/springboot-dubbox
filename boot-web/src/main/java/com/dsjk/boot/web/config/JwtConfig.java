package com.dsjk.boot.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengcheng
 * @version 2017/7/31
 */
@Configuration
public class JwtConfig {

    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtFilter jwtFilter = new JwtFilter();
        registrationBean.setFilter(jwtFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/sys/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
