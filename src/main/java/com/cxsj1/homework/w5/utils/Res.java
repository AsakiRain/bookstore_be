package com.cxsj1.homework.w5.utils;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.Response;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Res {
    public static void writeHTMLHeader(PrintWriter w, String title) {
        w.write("<!DOCTYPE html>\n" + "<html lang=\"zh-hans\">\n" + "  <head>\n" + "    <meta charset=\"UTF-8\" />\n" + "    <link rel=\"icon\" type=\"image/svg+xml\" href=\"/vite.svg\" />\n" + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" + "    <title>" + title + "</title>\n" + "  </head>\n" + "  <body>");
    }

    public static void writeHTMLFooter(PrintWriter w) {
        w.write("""
                  </body>
                </html>
                """);
    }

    public static void Json(HttpServletResponse res, int code, String message) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter w = res.getWriter();
        Response payload = new Response(code, message, null);
        w.write(JSON.toJSONString(payload));
    }
    public static void Json(HttpServletResponse res, int code, String message, Map<String,Object> data) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter w = res.getWriter();
        Response payload = new Response(code, message, data);
        w.write(JSON.toJSONString(payload));
    }
    public static void Error(HttpServletResponse res, int status, int code, String message) throws IOException {
        res.setStatus(status);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter w = res.getWriter();
        Response payload = new Response(code, message, null);
        w.write(JSON.toJSONString(payload));
    }

    public static void CORS(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        res.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
