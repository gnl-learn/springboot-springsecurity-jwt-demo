package com.demo.boot;

import com.demo.boot.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.*;

@SpringBootTest
class SecurityJwtApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }



    @Test
    public void test() {

        Map<String, Object> map = new HashMap<>(8);
        map.put("username", "gnl");
        map.put("state", true);
        map.put("authorities", "user:view");

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 60);

        String token = Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, "1122333")
                .setIssuer("gnl")
                .setIssuedAt(new Date())
                .setExpiration(instance.getTime())
                .compact();

        System.out.println(token);
    }

    @Test
    public void test1() {
        String token =

                "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MTQxMzIwMTksImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifSx7ImF1dGhvcml0eSI6InVzZXI6dmlldyJ9XSwidXNlcm5hbWUiOiJxd2UifQ.xN8EAjMX9xejplGtpPKSutw54m9OZuZvQS7T89e6o1bdumvFZls06C8TRosBlejk53ujVnuEHxluaDCVxEIPVg";

        Calendar instance = Calendar.getInstance();

        Claims claims = JwtTokenUtils.getTokenBody(token);
        System.out.println(instance.getTime());
        System.out.println(claims.getExpiration());
        System.out.println(claims.getExpiration().before(instance.getTime()));

    }




    @Value("#{'${request.white}'.split(',')}")
    private String[] whiteRequest;

    @Value("#{'${request.white}'.split(',')}")
    private List<String> white;

    @Test
    public void test2() {
        String req = "/test/hello";
        System.out.println(whiteRequest[0]);
        System.out.println(white);
    }

}
