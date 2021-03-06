package com.demo.boot.controller;

import com.demo.boot.service.UserService;
import com.demo.boot.util.JwtTokenUtils;
import com.demo.boot.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * TODO
 *
 * @author gnl
 * @date 2021-02-23 13:38
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "UserController ===> hello 你好啊";
    }

    @GetMapping("/msg")
    public String msg(){
        return "UserController ===> msg";
    }

    @Secured(value = {"ROLE_USER"})
    @GetMapping("/role")
    public Result role(){
        return Result.ok("操作成功", "UserController ===> role");
    }

    @PreAuthorize(value = "hasAuthority('user:view')")
    @GetMapping("/view")
    public Result authorityView(){
        return Result.ok("操作成功", "UserController ===> authority user:view");
    }

    @PreAuthorize(value = "hasAuthority('user:edit')")
    @GetMapping("/edit")
    public Result authorityEdit(){
        return Result.ok("操作成功", "UserController ===> authority user:edit");
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> map, HttpServletResponse response){

        String username = map.get("username");
        String password = map.get("password");

        try {

            String token = userService.login(username, password);
            response.setHeader(JwtTokenUtils.JWT_HEADER, token);
            return Result.ok("操作成功",token);

        } catch (Exception e) {
            return Result.fail(401, e.getMessage());
        }
    }

}
