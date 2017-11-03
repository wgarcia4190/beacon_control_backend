package com.sigma.beacon_control.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Wilson on 4/30/17.
 */
public class JwtManager {

    private static final String SECRET = "yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

    public static String generateToken(String email, long userId){
        String subject = userId + ":" + email;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256,  SECRET.getBytes())
                .compact();
    }

    public static String parseToken(String token){
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean isTokenValid(String token){
        return parseToken(token) != null;
    }


}
