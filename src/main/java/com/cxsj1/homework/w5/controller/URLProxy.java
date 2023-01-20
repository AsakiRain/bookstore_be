package com.cxsj1.homework.w5.controller;

import com.cxsj1.homework.w5.utils.Req;
import com.cxsj1.homework.w5.utils.Res;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
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
        } catch (Exception e) {
            Res.Error(res, 500, 50099, e.getMessage());
        }
    }
}