package com.cxsj1.homework.w4.model;

import com.cxsj1.homework.w4.database.DB;

import java.util.Map;

public class User {
    public String username;
    public String nickname;
    public String sex;
    public String password;

    public void set(String username, String nickname, String sex, String password) {
        this.nickname = nickname;
        this.username = username;
        this.sex = sex;
        this.password = password;
    }

    public static boolean hasUser(String username) {
        return DB.hasRecord("select * from users where username = ?", username);
    }

    public static String get(String username, User user) {
        Map<String, Object> map = DB.queryOne("select * from users where username = ?", username);
        if (map == null) {
            return "用户不存在";
        }
        user.set((String) map.get("username"), (String) map.get("nickname"), (String) map.get("sex"), (String) map.get("password"));
        return null;
    }

    public boolean save() {
        int affectedRows = DB.commit("update users set nickname = ?, sex = ?, password = ? where username = ?", this.nickname, this.sex, this.password, this.username);
        return affectedRows == 1;
    }

    public static boolean create(String username, String nickname, String password, String sex) {
        int affectedRows = DB.commit("insert into users (username, nickname, password, sex) values (?, ?, ?, ?)", username, nickname, password, sex);
        return affectedRows == 1;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}