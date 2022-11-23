package com.cxsj1.homework.w4.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w4.model.RegisterForm;
import com.cxsj1.homework.w4.model.User;
import com.cxsj1.homework.w4.utils.Req;
import com.cxsj1.homework.w4.utils.Res;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Res.Error(res, 400,40001, "不能使用GET请求");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
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

        if (User.create(username, nickname, password, sex)) {
            Res.Json(res, 20000, "注册成功");
        } else {
            Res.Json(res, 50000, "注册失败");

        }
    }
}
