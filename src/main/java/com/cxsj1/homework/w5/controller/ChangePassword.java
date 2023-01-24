package com.cxsj1.homework.w5.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.Claim;
import com.cxsj1.homework.w5.model.Form.PasswordForm;
import com.cxsj1.homework.w5.model.User;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/user/password")
public class ChangePassword extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String err;
        String token = req.getHeader("Authorization");
        if (token == null) {
            Res.Error(res, 401, 40101, "未登录");
            return;
        }

        Claim claim = new Claim();
        err = Token.parse(token, claim);
        if (err != null) {
            Res.Error(res, 401, 40102, err);
            return;
        }

        if (!User.hasUser(claim.username)) {
            Res.Json(res, 42205, "用户不存在");
            return;
        }
        User user = new User(claim.username);

        PasswordForm passwordForm = JSON.parseObject(req.getInputStream().readAllBytes(), PasswordForm.class);
        if (passwordForm == null) {
            Res.Error(res, 400, 40002, "参数不足或有误");
            return;
        }

        String currentPassword = passwordForm.current_password;
        String newPassword = passwordForm.new_password;
        if (!user.checkPassword(currentPassword)) {
            Res.Json(res, 42205, "密码错误");
            return;
        }

        user.password = newPassword;
        user.save();

        Res.Json(res, 20000, "修改密码成功");
    }
}