package com.cxsj1.homework.w4.controller;

import com.cxsj1.homework.w4.utils.Req;
import com.cxsj1.homework.w4.utils.Res;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

import okhttp3.*;

@WebServlet("/api/proxy")
public class URLProxy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Res.CORS(res);
        String url = req.getParameter("url");
        if (Req.hasEmpty(url)) {
            Res.Error(res, 422, 42299, "缺少参数");
            return;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            res.setContentType(response.header("Content-Type"));
            String setCookie = response.header("Set-Cookie");
            if (setCookie != null) {
                res.setHeader("Set-Cookie", setCookie);
            }
            Objects.requireNonNull(response.body()).byteStream().transferTo(res.getOutputStream());
        }
    }
}