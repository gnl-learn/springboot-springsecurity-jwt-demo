package com.demo.boot.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * USELESS
 * UserAuthenticationFailureHandler 用户登录失败处理类
 *
 * @author gnl
 */

@Slf4j
//@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        log.info("UserAuthenticationFailureHandler ===> onAuthenticationFailure");

        if (e instanceof UsernameNotFoundException) {
            log.info("===> UsernameNotFoundException");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
        if (e instanceof BadCredentialsException) {
            log.info("===> BadCredentialsException");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());

    }
}
