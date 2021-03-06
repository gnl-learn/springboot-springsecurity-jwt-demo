package com.demo.boot.util;

import io.jsonwebtoken.*;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtTokenUtil
 *
 * @author gnl
 * @date 2021-02-21 18:03
 */

@Getter
public class JwtTokenUtils {

    public static final String JWT_HEADER = "Authentication";
    public static final String JWT_PREFIX = "Bearer ";

    private static final String JWT_SIGN_SECRET = "sdWEV6*^.53.@#$";
    private static final String JWT_ISSUER = "gnl";

    /**
     * 过期时间为1小时
     */
    private static final Integer JWT_EXPIRATION = 1;

    /**
     * 记住我过期时间为7天
     */
    private static final Integer JWT_EXPIRATION_REMEMBER = 24*7;

    public static Calendar calendar = Calendar.getInstance();

    public static Date setExpiration(Integer time) {
        calendar.add(Calendar.HOUR, time);
        return calendar.getTime();
    }

    /**
     * 创建token
     *
     * @author gnl
     * @date 2021/2/20 17:51
     */
    public static String createToken(String username, boolean isRememberMe) {
        int expiration = isRememberMe ? JWT_EXPIRATION_REMEMBER : JWT_EXPIRATION;

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JWT_SIGN_SECRET)
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(calendar.getTime())
                .setSubject(username)
                .setExpiration(setExpiration(expiration))
                .compact();
    }

    public static String createToken(UserDetails userDetails) {

        Map<String, Object> map = new HashMap<>(8);
        map.put("username", userDetails.getUsername());
        map.put("authorities", userDetails.getAuthorities());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JWT_SIGN_SECRET)
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(calendar.getTime())
                .setClaims(map)
                .setExpiration(setExpiration(JWT_EXPIRATION))
                .compact();
    }

    public static String createToken(Map<String, Object> map, boolean isRememberMe) {
        int expiration = isRememberMe ? JWT_EXPIRATION_REMEMBER : JWT_EXPIRATION;

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JWT_SIGN_SECRET)
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(calendar.getTime())
                .setClaims(map)
                .setExpiration(setExpiration(expiration))
                .compact();
    }

    /**
     * 获得token体
     *
     * @author gnl
     * @date 2021/2/21 18:34
     */
    public static Claims getTokenBody(String token) {

        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(JWT_SIGN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 即使过期也获取claims
            claims = e.getClaims();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 检查token是否过期
     *
     * @author gnl
     * @date 2021/2/21 18:34
     */
    public static Boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(calendar.getTime());
    }

    /**
     * 获得token body
     *
     * @author gnl
     * @date 2021/2/21 18:34
     */
    public static String getTokenSubject(String token) {
        return getTokenBody(token).getSubject();
    }


    /**
     * 刷新令牌
     *
     * @author gnl
     * @date 2021/2/23 17:19
     * @param oldToken
     * @return java.lang.String
     */
    public static String refreshToken(String oldToken) {

        Claims claims = getTokenBody(oldToken);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JWT_SIGN_SECRET)
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(calendar.getTime())
                .setClaims(claims)
                .setExpiration(setExpiration(JWT_EXPIRATION))
                .compact();
    }

}
