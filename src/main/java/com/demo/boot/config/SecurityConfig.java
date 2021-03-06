package com.demo.boot.config;

import com.demo.boot.security.filter.JwtTokenAuthenticationFilter;
import com.demo.boot.security.handler.UserAccessDeniedHandler;
import com.demo.boot.security.handler.UserAuthenticationEntryPointHandler;
import com.demo.boot.security.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * TODO
 *
 * @author gnl
 * @date 2021-02-23 14:32
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("#{'${request.white}'.split(',')}")
    private String[] whiteRequest;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;

    @Autowired
    UserAccessDeniedHandler userAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()

                .authorizeRequests().antMatchers(whiteRequest).permitAll()
                .anyRequest().authenticated()

                .and().exceptionHandling()
                .accessDeniedHandler(userAccessDeniedHandler)
                .authenticationEntryPoint(userAuthenticationEntryPointHandler)

                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().addFilterBefore(new JwtTokenAuthenticationFilter(userDetailsService, handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);

    }
}
