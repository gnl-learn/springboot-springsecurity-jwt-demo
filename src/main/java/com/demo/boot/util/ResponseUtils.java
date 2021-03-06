package com.demo.boot.util;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * ResponseUtils
 *
 * @author gnl
 * @date 2021-02-23 21:05
 */

public class ResponseUtils {

    /**
     * 给页面响应json
     *
     * @author gnl
     * @date 2021/2/23 21:47
     * @param response
     * @param resultMap
     * @return void
     */
    public static void responseJson(HttpServletResponse response, Map<String, Object> resultMap) {

        PrintWriter out = null;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {

            JSONObject json = new JSONObject(resultMap);

            out = response.getWriter();
            out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                out.flush();
                out.close();
            }
        }

    }

    public static Map<String, Object> resultError(int code, String msg) {
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("success", false);
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("data", null);
        return resultMap;
    }

}
