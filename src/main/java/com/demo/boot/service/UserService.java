package com.demo.boot.service;

import com.demo.boot.security.impl.UserDetailsServiceImpl;
import com.demo.boot.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author gnl
 * @date 2021-02-23 14:38
 */

@Slf4j
@Service
public class UserService {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * login
     *
     * @author gnl
     * @date 2021/2/23 14:38
     * @param username
     * @param password
     * @return java.lang.String
     */
    public String login(String username, String password) throws BadCredentialsException{

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 验证用户传过来的密码和数据库保存的密码是否一致
        boolean matches = passwordEncoder.matches(password, userDetails.getPassword());

        if (matches) {
            return JwtTokenUtils.JWT_PREFIX + JwtTokenUtils.createToken(userDetails);
        }

        throw new BadCredentialsException("密码不正确");

    }

}
