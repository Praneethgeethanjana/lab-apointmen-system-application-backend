package com.praneeth.lab.config.security;


import com.praneeth.lab.interceptor.RequestHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorAppConfig implements WebMvcConfigurer {

    @Autowired
    RequestHandlerInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {

        interceptorRegistry.addInterceptor(interceptor).excludePathPatterns(
                "/public-user/**",
                "/application/**");

    }

}
