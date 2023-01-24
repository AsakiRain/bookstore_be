package com.cxsj1.homework.w5.controller;

import com.cxsj1.homework.w5.model.Stock;
import com.cxsj1.homework.w5.utils.Res;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/book/show")
public class ShowBook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
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

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("book_info", stock);
            }
        };
        Res.Json(res, 20000, "获取成功", data);
    }
}