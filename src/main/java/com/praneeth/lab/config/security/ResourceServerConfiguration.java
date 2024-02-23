package com.praneeth.lab.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;



@Configuration
@EnableResourceServer
@EnableAsync
@RequiredArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";

    private final Environment environment;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        String base = environment.getRequiredProperty("spring.mvc.servlet.path");
        httpSecurity
                .authorizeRequests()
                .antMatchers(base + "/application/**",
                        base + "/public-user/**")
                .permitAll()

                .antMatchers(HttpMethod.PATCH,base + "/auth/logout")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")

                .antMatchers(base + "/admin/**")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers(base + "/user/**")
                .access("hasAnyRole('ROLE_USER')")

                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
