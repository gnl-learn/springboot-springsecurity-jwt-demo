package com.demo.boot.exception;

import com.demo.boot.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.sasl.AuthenticationException;

/**
 * GlobalExceptionHandler 全局异常处理类
 *
 * @author gnl
 * @date 2021-02-24 12:10
 */

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception exception) {
        log.info("GlobalExceptionHandler ===> handlerException");
        return Result.fail(555, exception.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result handlerAuthenticationException(AuthenticationException exception) {
        log.info("GlobalExceptionHandler ===> handlerAuthenticationException");
        return Result.fail(401, exception.getMessage());
    }

}
