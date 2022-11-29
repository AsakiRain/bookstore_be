package com.cxsj1.homework.w4.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w4.model.Book;
import com.cxsj1.homework.w4.model.BookForm;
import com.cxsj1.homework.w4.model.Claim;
import com.cxsj1.homework.w4.utils.Req;
import com.cxsj1.homework.w4.utils.Res;
import com.cxsj1.homework.w4.utils.Token;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/book")
public class BookCRUD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String err;

        String isbn = req.getParameter("isbn");
        if (isbn == null) {
            Res.Error(res, 422, 42201, "缺少参数");
            return;
        }

        Book book = new Book();
        err = book.get(isbn);
        if (err != null) {
            Res.Json(res, 42205, err);
            return;
        }

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("book_info", book);
            }
        };
        Res.Json(res, 20000, "获取成功", data);
    }

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

        BookForm bookForm = JSON.parseObject(req.getInputStream().readAllBytes(), BookForm.class);
        if (bookForm == null) {
            Res.Error(res, 400, 40002, "参数不足");
            return;
        }

        if (Req.hasEmpty(bookForm.isbn, bookForm.title)) {
            Res.Error(res, 422, 42201, "isbn和title不能为空");
            return;
        }

        if (Book.hasBook(bookForm.isbn)) {
            Res.Json(res, 42202, "图书已存在");
            return;
        }

        if (!Book.create(bookForm)) {
            Res.Json(res, 42205, "创建图书失败");
            return;
        }

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("book_info", bookForm);
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

        BookForm bookForm = JSON.parseObject(req.getInputStream().readAllBytes(), BookForm.class);
        if (bookForm == null) {
            Res.Error(res, 400, 40002, "参数不足");
            return;
        }

        if (Req.hasEmpty(bookForm.isbn, bookForm.title)) {
            Res.Error(res, 422, 42201, "isbn和title不能为空");
            return;
        }

        Book book = new Book();
        err = book.get(bookForm.isbn);
        if (err != null) {
            Res.Json(res, 42205, err);
            return;
        }

        book.set(bookForm);
        if (!book.save()) {
            Res.Json(res, 42205, "更新图书失败");
            return;
        }

        HashMap<String, Object> data = new HashMap<>() {
            {
                put("book_info", bookForm);
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

        if (!Book.delete(isbn)) {
            Res.Json(res, 42205, "没有这本书");
            return;
        }

        Res.Json(res, 20000, "删除图书成功");
    }
}
