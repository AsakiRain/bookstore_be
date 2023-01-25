package com.cxsj1.homework.w5.controller;

import com.cxsj1.homework.w5.model.*;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/order/show")
public class ShowOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String token = req.getHeader("Authorization");
        Claim claim = new Claim();
        Token.parse(token, claim);

        String serial = req.getParameter("serial");
        if (serial == null) {
            Res.Error(res, 422, 42201, "缺少参数");
            return;
        }

        if (!Order.hasOrder(serial)) {
            Res.Error(res, 404, 40401, "订单不存在");
            return;
        }

        Order order = new Order(serial);
        if (!claim.username.equals(order.username)) {
            Res.Error(res, 403, 40301, "不是你的订单");
            return;
        }

        User user = new User(order.username);
        Stock stock;
        if (Stock.hasBook(order.isbn)) {
            stock = new Stock(order.isbn);
        } else {
            stock = null;
        }

        Statistic statistic = new Statistic(new java.util.Date());
        statistic.addTotalView();

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("user_info", user);
                put("goods_info", stock);
                put("order_info", order);
            }
        };
        Res.Json(res, 20000, "获取成功", data);
    }
}