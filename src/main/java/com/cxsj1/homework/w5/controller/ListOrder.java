package com.cxsj1.homework.w5.controller;

import com.cxsj1.homework.w5.model.Claim;
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

@WebServlet("/api/order/list")
public class ListOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String token = req.getHeader("Authorization");
        Claim claim = new Claim();
        Token.parse(token, claim);

        int page;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException e) {
            Res.Error(res, 422, 42202, e.getMessage());
            return;
        }

        Statistic statistic = new Statistic(new java.util.Date());
        statistic.addTotalView();

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("order_list", Order.list(page, claim.username));
                put("order_count", Order.countList(claim.username));
            }
        };

        Res.Json(res, 20000, "获取成功", data);
    }
}
