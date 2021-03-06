package com.demo.boot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TODO
 *
 * @author gnl
 * @date 2021-02-22 17:48
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Boolean success;
    private Integer code;
    private String msg;
    private Object data;

    public static Result ok(String msg, Object data){
        return new Result(true, 200, msg, data);
    }

    public static Result ok(String msg){
        return new Result(true, 200, msg, null);
    }

    public static Result fail(int code, String msg){
        return new Result(false, code, msg, null);
    }

}
