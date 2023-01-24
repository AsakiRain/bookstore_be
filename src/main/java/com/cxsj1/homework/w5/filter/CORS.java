package com.cxsj1.homework.w5.filter;

import java.io.IOException;

import com.cxsj1.homework.w5.Config;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cxsj1.homework.w5.Config.CORS_CONFIG;

@WebFilter(filterName = "CORS", urlPatterns = "/*")
public class CORS implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        System.out.println("::CORS at ".concat(req.getRequestURI()));
        res.setHeader("Access-Control-Allow-Origin", CORS_CONFIG.ALLOW_ORIGIN);
        res.setHeader("Access-Control-Allow-Methods", CORS_CONFIG.ALLOW_METHODS);
        res.setHeader("Access-Control-Allow-Headers", CORS_CONFIG.ALLOW_HEADERS);
        res.setHeader("Access-Control-Allow-Credentials", CORS_CONFIG.ALLOW_CREDENTIALS);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}