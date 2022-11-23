package com.cxsj1.homework.w4.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cxsj1.homework.w4.Config;
import com.cxsj1.homework.w4.model.Claim;

import java.util.Date;

public class Token {
    public static final Algorithm KEY = Algorithm.HMAC256(Config.JWT.SECRET);
    public static final String ISSUER = Config.JWT.ISSUER;

    public static String create(String username, String nickname, String sex) {
        try {
            return JWT.create().withIssuer(ISSUER).withClaim("username", username).withExpiresAt(Time.elapseDay(7)).sign(KEY);
        } catch (JWTCreationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parse(String token, Claim claim) {
        try {
            DecodedJWT claims = JWT.require(KEY).withIssuer(ISSUER).build().verify(token);
            if (claims == null) {
                return "解析token失败";
            }
            claim.set(claims.getClaim("username").asString(), claims.getClaim("nickname").asString(), claims.getClaim("sex").asString());
        } catch (Exception e) {
//            e.printStackTrace();
            return e.getMessage();
        }
        return null;
    }
}
