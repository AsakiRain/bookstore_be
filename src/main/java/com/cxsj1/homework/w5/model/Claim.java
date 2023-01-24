package com.cxsj1.homework.w5.model;

import com.auth0.jwt.interfaces.DecodedJWT;

public class Claim {
    public String username;
    public String nickname;
    public String sex;

    public String role;

    public void set(DecodedJWT claims) {
        this.username = claims.getClaim("username").asString();
        this.nickname = claims.getClaim("nickname").asString();
        this.sex = claims.getClaim("sex").asString();
        this.role = claims.getClaim("role").asString();
    }
}
