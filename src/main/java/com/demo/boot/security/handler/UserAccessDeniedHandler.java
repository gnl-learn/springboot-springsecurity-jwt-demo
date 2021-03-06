package com.demo.boot.security.handler;

import com.demo.boot.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * UserDeniedAccessHandler 用户无权访问处理类
 *
 * @author gnl
 * @date 2021-02-23 18:52
 */

@Slf4j
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {

        log.info("UserAccessDeniedHandler ===> handle");

        ResponseUtils.responseJson(response, ResponseUtils.resultError(403, "用户无权访问该资源"));

    }
}
