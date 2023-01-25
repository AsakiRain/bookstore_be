package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;
import com.cxsj1.homework.w5.model.Form.RegisterForm;
import com.cxsj1.homework.w5.model.Form.UserInfoForm;

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

    public User(String username) {
        this._get(username);
    }

    public User(RegisterForm registerForm) {
        int affectedRows = DB.commit("insert into users (username, nickname, password, sex, balance, role) values (?,"
                + " ?, ?, ?, ?, ?)", registerForm.username, registerForm.nickname, registerForm.password,
                registerForm.sex, 648, "customer");
        if (affectedRows == 1) {
            this._get(registerForm.username);
        } else {
            throw new RuntimeException("用户创建失败");
        }
    }

    private void _set(HashMap<String, Object> data) {
        this.username = (String) data.get("username");
        this.nickname = (String) data.get("nickname");
        this.password = (String) data.get("password");
        this.sex = (String) data.get("sex");
        this.balance = (int) data.get("balance");
        this.role = (String) data.get("role");
        this.created_at = ((Date) data.get("created_at")).getTime();
        this.updated_at = ((Date) data.get("updated_at")).getTime();
    }

    public void set(UserInfoForm userInfoForm) {
        this.nickname = userInfoForm.nickname;
        this.sex = userInfoForm.sex;
    }

    public static boolean hasUser(String username) {
        return DB.hasRecord("select * from users where username = ?", username);
    }

    private void _get(String username) {
        HashMap<String, Object> map = DB.queryOne("select * from users where username = ?", username);
        if (map.size() == 0) throw new RuntimeException("!!用户不存在: " + username);
        this._set(map);
    }

    public void save() {
        int affectedRows =
                DB.commit("update users set nickname = ?, sex = ?, password = ?, balance = ?, role = ? " + "where " + "username = ?", this.nickname, this.sex, this.password, this.balance, this.role, this.username);
        if (affectedRows != 1) throw new RuntimeException("!!更新用户信息失败: " + this.username);

    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void subBalance(int amount) {
        int affectedRows = DB.commit("update users set balance = balance - ? where username = ?", amount,
                this.username);
        if (affectedRows != 1) throw new RuntimeException("!!余额扣除失败: " + this.username + " " + amount);
        this.balance -= amount;
    }
}