package com.cxsj1.homework.w5.model;

public class Claim {
    public String username;
    public String nickname;
    public String sex;

    public String role;

    public void set(String username, String nickname, String sex, String role) {
        this.username = username;
        this.nickname = nickname;
        this.sex = sex;
        this.role = role;
    }
}
