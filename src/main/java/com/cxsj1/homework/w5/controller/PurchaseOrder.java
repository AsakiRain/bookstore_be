package com.cxsj1.homework.w5.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.*;
import com.cxsj1.homework.w5.model.Form.PurchaseOrderForm;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/order/purchase")
public class PurchaseOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String token = req.getHeader("Authorization");
        Claim claim = new Claim();
        Token.parse(token, claim);
        User user = new User(claim.username);

        String isbn = req.getParameter("isbn");
        if (isbn == null) {
            Res.Error(res, 422, 42201, "缺少参数");
            return;
        }

        if (!Stock.hasBook(isbn)) {
            Res.Error(res, 404, 40401, "没有这本书");
            return;
        }
        Stock stock = new Stock(isbn);

        Statistic statistic = new Statistic(new java.util.Date());
        statistic.addTotalView();

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("user_info", user);
                put("goods_info", stock);
                put("remain_balance", user.balance - stock.cost);
                put("can_purchase", stock.for_sale && user.balance >= stock.cost && stock.stock > 0);
            }
        };
        Res.Json(res, 20000, "获取成功", data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String token = req.getHeader("Authorization");
        Claim claim = new Claim();
        Token.parse(token, claim);
        User user = new User(claim.username);

        PurchaseOrderForm purchaseOrderForm = JSON.parseObject(req.getInputStream().readAllBytes(),
                PurchaseOrderForm.class);
        if (purchaseOrderForm == null) {
            Res.Error(res, 400, 40002, "参数不足或有误");
            return;
        }

        String isbn = purchaseOrderForm.isbn;
        if (!Stock.hasBook(isbn)) {
            Res.Error(res, 404, 40401, "没有这本书");
            return;
        }
        Stock stock = new Stock(isbn);

        if (!stock.for_sale) {
            Res.Error(res, 422, 42200, "该商品已下架");
        }

        if (user.balance < stock.cost) {
            Res.Error(res, 422, 42200, "积分不足");
        }
        if (stock.stock <= 0) {
            Res.Error(res, 422, 42200, "库存不足");
        }

        Order order = new Order(user, stock);
        user.subBalance(stock.cost);
        stock.subStock();

        Statistic statistic = new Statistic(new java.util.Date());
        statistic.addTotalView();
        statistic.addPurchaseCount();

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("serial", order.serial);
                put("user_info", user);
                put("goods_info", stock);
                put("order_info", order);
            }
        };
        Res.Json(res, 20000, "下单成功！", data);
    }
}
