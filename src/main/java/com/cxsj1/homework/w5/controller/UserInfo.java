package com.cxsj1.homework.w5.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.Claim;
import com.cxsj1.homework.w5.model.User;
import com.cxsj1.homework.w5.model.Form.UserInfoForm;
import com.cxsj1.homework.w5.utils.Req;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/user/info")
public class UserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
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

        Map<String, Object> data = new HashMap<>() {
            {
                put("user_info", user);
            }
        };

        Res.Json(res, 20000, "获取成功", data);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String token = req.getHeader("Authorization");
        Claim claim = new Claim();
        Token.parse(token, claim);
        User user = new User(claim.username);

        UserInfoForm userInfoForm = JSON.parseObject(req.getInputStream().readAllBytes(), UserInfoForm.class);
        if (userInfoForm == null) {
            Res.Error(res, 400, 40002, "参数不足或有误");
            return;
        }

        String nickname = userInfoForm.nickname;
        String sex = userInfoForm.sex;
        if (Req.hasEmpty(nickname, sex)) {
            Res.Error(res, 400, 40002, "参数不足或有误");
            return;
        }

        user.nickname = nickname;
        user.sex = sex;
        user.save();

        Res.Json(res, 20000, "更新信息成功");
    }
}
