package com.demo.boot.security.filter;

import com.demo.boot.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * JwtTokenAuthenticationFilter 继承自 OncePerRequestFilter 每个请求都会经过这个过滤器
 *
 * 注意：Filter中无法使用自动注入功能（@Value，@Autowired）
 *
 * @author gnl
 * @date 2021-02-23 14:47
 */

@Slf4j
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtTokenAuthenticationFilter(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("JwtTokenAuthenticationFilter ===> doFilterInternal");

        log.info(request.getRequestURI());

        String token = request.getHeader(JwtTokenUtils.JWT_HEADER);

        // token不为空
        if (StringUtils.hasLength(token)) {
            log.info(token);
            // 解析token
            token = token.replace(JwtTokenUtils.JWT_PREFIX, "");

            // try catch

            // 令牌没过期
            if (!JwtTokenUtils.isExpiration(token)){

                log.info("令牌有效");

                Claims claims = JwtTokenUtils.getTokenBody(token);
                String username = claims.get("username", String.class);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                // 对使用该令牌的用户进行授权
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

                // 用户是否已授权
                boolean authenticated = authentication.isAuthenticated();
                log.info(String.valueOf(authenticated));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 刷新令牌
                // String newToken = JwtTokenUtils.refreshToken(token);
                // response.setHeader(JwtTokenUtils.JWT_HEADER, JwtTokenUtils.JWT_PREFIX + newToken);

            } else {

                log.info("令牌过期");

                // 令牌过期，重新登录
                // ...

                // 抛出能被全局异常处理类捕获到的异常
                handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("登录身份过期，请重新登录"));
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
