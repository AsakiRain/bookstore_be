package com.cxsj1.homework.w5.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.Form.RegisterForm;
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

@WebServlet("/api/register")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String err;
        RegisterForm registerForm = JSON.parseObject(req.getInputStream().readAllBytes(), RegisterForm.class);
        if (registerForm == null) {
            Res.Error(res, 400, 40002, "参数不足或有误");
            return;
        }

        String username = registerForm.username;
        String password = registerForm.password;
        String nickname = registerForm.nickname;
        String sex = registerForm.sex;
        if (Req.hasEmpty(username, nickname, password, sex)) {
            Res.Error(res, 400, 40000, "参数不足或有误");
            return;
        }

        if (User.hasUser(username)) {
            Res.Json(res, 42201, "用户名已存在");
            return;
        }

        if (!User.create(username, nickname, password, sex)) {
            Res.Json(res, 50000, "注册失败");
            return;
        }

        User user = new User();
        err = User.get(username, user);
        if (err != null) {
            Res.Json(res, 42203, err);
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
        Res.Json(res, 20000, "注册成功", data);
    }
}
