package com.cxsj1.homework.w5.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.Stock;
import com.cxsj1.homework.w5.model.Claim;
import com.cxsj1.homework.w5.model.Form.StockForm;
import com.cxsj1.homework.w5.utils.Req;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/manage/stock")
public class ManageStock extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
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

        StockForm stockForm = JSON.parseObject(req.getInputStream().readAllBytes(), StockForm.class);
        if (stockForm == null) {
            Res.Error(res, 400, 40002, "参数不足");
            return;
        }

        if (Req.hasEmpty(stockForm.isbn, stockForm.title, stockForm.cost, stockForm.stock, stockForm.for_sale)) {
            Res.Error(res, 422, 42201, "参数校验不通过");
            return;
        }

        if (Stock.hasBook(stockForm.isbn)) {
            Res.Json(res, 42202, "图书已存在");
            return;
        }

        Stock stock = new Stock(stockForm);

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("book_info", stock);
            }
        };
        Res.Json(res, 20000, "创建图书成功", data);
    }

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

        StockForm stockForm = JSON.parseObject(req.getInputStream().readAllBytes(), StockForm.class);
        if (stockForm == null) {
            Res.Error(res, 400, 40002, "参数不足");
            return;
        }

        if (Req.hasEmpty(stockForm.isbn, stockForm.title)) {
            Res.Error(res, 422, 42201, "isbn和title不能为空");
            return;
        }

        if (!Stock.hasBook(stockForm.isbn)) {
            Res.Json(res, 42203, "图书不存在");
            return;
        }

        Stock stock = new Stock(stockForm.isbn);
        stock.set(stockForm);
        stock.save();

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("book_info", stock);
            }
        };
        Res.Json(res, 20000, "更新图书成功", data);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
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

        String isbn = req.getParameter("isbn");
        if (isbn == null) {
            Res.Error(res, 422, 42201, "缺少参数");
            return;
        }

        if (!Stock.delete(isbn)) {
            Res.Json(res, 42205, "没有这本书");
            return;
        }

        Res.Json(res, 20000, "删除图书成功");
    }
}
