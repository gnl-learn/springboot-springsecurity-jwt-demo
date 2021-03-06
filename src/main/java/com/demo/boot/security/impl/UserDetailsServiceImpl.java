package com.demo.boot.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.boot.bean.User;
import com.demo.boot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * UserDetailsServiceImpl
 *
 * @author gnl
 * @date 2021-02-23 14:36
 */

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(username);

        if (StringUtils.hasLength(username)) {

            // 加载用户基础信息
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            User user = userMapper.selectOne(wrapper);

            if (user != null){

                // 获得用户角色
                // 通过用户角色列表加载用户的资源列表
                // String role = user.getRole();
                // ...

                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER", "user:view");

                return new org.springframework.security.core.userdetails.User(username, passwordEncoder.encode(user.getPassword()), authorities);

            }
            throw new UsernameNotFoundException("用户不存在");
        }
        throw new UsernameNotFoundException("用户名为空");
    }
}
