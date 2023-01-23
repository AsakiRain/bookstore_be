package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;

import java.util.Date;
import java.util.HashMap;

public class User {
    public String username;
    public String nickname;
    public String sex;
    public transient String password;
    public int balance;
    public String role;
    public Long created_at;
    public Long updated_at;

    public void set(HashMap<String, Object> data) {
        this.username = (String) data.get("username");
        this.nickname = (String) data.get("nickname");
        this.password = (String) data.get("password");
        this.sex = (String) data.get("sex");
        this.balance = (int) data.get("balance");
        this.role = (String) data.get("role");
        this.created_at = ((Date) data.get("created_at")).getTime();
        this.updated_at = ((Date) data.get("updated_at")).getTime();
    }

    public static boolean hasUser(String username) {
        return DB.hasRecord("select * from users where username = ?", username);
    }

    public static String get(String username, User user) {
        HashMap<String, Object> map = DB.queryOne("select * from users where username = ?", username);
        if (map.size() == 0) {
            return "用户不存在";
        }
        user.set(map);
        return null;
    }

    public boolean save() {
        int affectedRows =
                DB.commit("update users set nickname = ?, sex = ?, password = ?, balance = ?, role = ? " + "where username = ?",
                        this.nickname, this.sex, this.password, this.balance, this.role, this.username);
        return affectedRows == 1;
    }

    public static boolean create(String username, String nickname, String password, String sex, int balance, String role) {
        int affectedRows = DB.commit("insert into users (username, nickname, password, sex, balance, role) values (?, ?, ?, ?, ?, ?)",
                username, nickname, password, sex, balance, role);
        return affectedRows == 1;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}