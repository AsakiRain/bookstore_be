package com.cxsj1.homework.w4;

import com.cxsj1.homework.w4.utils.Res;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/search")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter w = resp.getWriter();
        Res.writeHTMLHeader(w, "Hello Servlet");
        w.write("<h1>Hello Servlet</h1>");
        Res.writeHTMLFooter(w);
    }
}
