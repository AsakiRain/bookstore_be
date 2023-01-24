package com.cxsj1.homework.w5.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.Form.LoginForm;
import com.cxsj1.homework.w5.model.User;
import com.cxsj1.homework.w5.utils.Req;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String err;
        LoginForm loginForm = JSON.parseObject(req.getInputStream().readAllBytes(), LoginForm.class);
        if (loginForm == null) {
            Res.Error(res, 400, 40002, "用户名或密码不能为空");
            return;
        }

        String username = loginForm.username;
        String password = loginForm.password;
        if (Req.hasEmpty(username, password)) {
            Res.Error(res, 400, 40000, "用户名或密码不能为空");
            return;
        }

        if (!User.hasUser(username)) {
            Res.Json(res, 42201, "用户名或密码错误");
            return;
        }

        User user = new User(username);
        if (!user.checkPassword(password)) {
            Res.Json(res, 42204, "用户名或密码错误");
            return;
        }

        String token = Token.create(user.username, user.nickname, user.sex);
        if (token == null) {
            Res.Error(res, 500, 50000, "未能生成token");
            return;
        }

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("token", token);
            }
        };
        res.addCookie(new Cookie("token", token));
        Res.Json(res, 20000, "登录成功", data);
    }
}