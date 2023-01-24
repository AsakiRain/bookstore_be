package com.cxsj1.homework.w5.filter;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(filterName = "Encoding", urlPatterns = "/*")
public class F0_Encoding implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("Encoding Filter");
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}