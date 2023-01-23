package com.cxsj1.homework.w5.controller;

import com.cxsj1.homework.w5.model.Stock;
import com.cxsj1.homework.w5.model.BookList;
import com.cxsj1.homework.w5.model.Claim;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/api/booklist/search")
public class BookListSearch extends HttpServlet {
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

        String keyword = req.getParameter("keyword");
        if (keyword == null) {
            Res.Error(res, 422, 42201, "缺少参数");
            return;
        }

        BookList bookList = new BookList(claim.username);
        ArrayList<Stock> search_result = bookList.search(keyword);
        HashMap<String, Object> data = new HashMap<>() {
            {
                put("search_result", search_result);
                put("search_count", search_result.size());
            }
        };

        Res.Json(res, 20000, "获取成功", data);
    }
}
