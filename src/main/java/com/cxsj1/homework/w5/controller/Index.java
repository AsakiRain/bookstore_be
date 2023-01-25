package com.cxsj1.homework.w5.controller;

import com.cxsj1.homework.w5.model.Statistic;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Statistic statistic = new Statistic(new java.util.Date());
        statistic.addTotalView();

        res.setContentType("text/html");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write("CORS测试，请打开dev tools查看");
    }
}
