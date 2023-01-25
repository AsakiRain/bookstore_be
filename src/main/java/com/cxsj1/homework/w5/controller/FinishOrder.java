package com.cxsj1.homework.w5.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.Claim;
import com.cxsj1.homework.w5.model.Form.FinishOrderForm;
import com.cxsj1.homework.w5.model.Order;
import com.cxsj1.homework.w5.model.Statistic;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/order/finish")
public class FinishOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String token = req.getHeader("Authorization");
        Claim claim = new Claim();
        Token.parse(token, claim);

        FinishOrderForm finishOrderForm = JSON.parseObject(req.getInputStream().readAllBytes(), FinishOrderForm.class);
        if (finishOrderForm == null) {
            Res.Error(res, 400, 40002, "参数不足或有误");
            return;
        }

        String serial = finishOrderForm.serial;
        if (!Order.hasOrder(serial)) {
            Res.Error(res, 404, 40401, "订单不存在");
            return;
        }

        Order order = new Order(serial);
        if (!claim.username.equals(order.username)) {
            Res.Error(res, 403, 40301, "不是你的订单");
            return;
        }

        if (order.status < 2) {
            Res.Error(res, 422, 40302, "你还不能结束这个订单");
            return;
        }

        if (order.status == 3) {
            Res.Error(res, 422, 40303, "订单已经结束");
            return;
        }

        order.status = 3;
        order.save();

        Statistic statistic = new Statistic(new java.util.Date());
        statistic.addTotalView();
        statistic.addDealCount();
        statistic.addDealIncome(order.cost);
        
        HashMap<String, Object> data = new HashMap<>() {
            {
                put("order_info", order);
            }
        };
        Res.Json(res, 20000, "订单完成", data);
    }
}