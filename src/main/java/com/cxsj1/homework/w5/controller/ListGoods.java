package com.cxsj1.homework.w5.controller;

import com.cxsj1.homework.w5.model.Claim;
import com.cxsj1.homework.w5.model.Goods;
import com.cxsj1.homework.w5.model.Statistic;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/goods/list")
public class ListGoods extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (req.getParameter("page") == null) {
            Res.Error(res, 422, 42201, "缺少参数");
            return;
        }

        int page;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException e) {
            Res.Error(res, 422, 42202, e.getMessage());
            return;
        }

        Statistic statistic = new Statistic(new java.util.Date());
        statistic.addTotalView();
        statistic.addPageView();

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("goods_list", Goods.list(page));
                put("goods_count", Goods.countList());
            }
        };

        Res.Json(res, 20000, "获取成功", data);
    }
}
