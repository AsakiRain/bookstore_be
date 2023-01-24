package com.cxsj1.homework.w5.filter;

import com.cxsj1.homework.w5.model.Claim;
import com.cxsj1.homework.w5.model.User;
import com.cxsj1.homework.w5.utils.Res;
import com.cxsj1.homework.w5.utils.Token;
import com.cxsj1.homework.w5.utils.URLPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cxsj1.homework.w5.Config.PERMISSION_CONFIG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@WebFilter(filterName = "Permission", urlPatterns = "/*")
public class F2_Permission implements Filter {
    private HashMap<String, Integer> roleMap;
    private ArrayList<String> PUBLIC_METHOD;
    private String[] PUBLIC_PATH;
    private String[] CUSTOMER_PATH;
    private String[] MANAGER_PATH;
    private String[] ADMIN_PATH;
    private URLPath urlPath;

    private boolean LOG;

    @Override
    public void init(FilterConfig filterConfig) {
        // 读取配置项
        this.PUBLIC_METHOD = new ArrayList<>(Arrays.asList(PERMISSION_CONFIG.PUBLIC_METHOD));
        this.PUBLIC_PATH = PERMISSION_CONFIG.PUBLIC_PATH;
        this.CUSTOMER_PATH = PERMISSION_CONFIG.CUSTOMER_PATH;
        this.MANAGER_PATH = PERMISSION_CONFIG.MANAGER_PATH;
        this.ADMIN_PATH = PERMISSION_CONFIG.ADMIN_PATH;
        this.LOG = PERMISSION_CONFIG.LOG;
        this.urlPath = new URLPath(PERMISSION_CONFIG.PREFIX, PERMISSION_CONFIG.VERBOSE);
        this.roleMap = new HashMap<>() {
            {
                put("public", 0);
                put("customer", 1);
                put("manager", 2);
                put("admin", 3);
            }
        };
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("Permission Filter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // OPTIONS方法直接放行
        String method = req.getMethod();
        if (this.PUBLIC_METHOD.contains(method)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 开始匹配请求路径所需的权限
        String requiredRole = "";
        String matchLog;
        String path = req.getServletPath();
        // 权限的检测是倒着来的，考虑到公共路径可能是其他路径的前缀，所以先检测公共路径
        // 先检测是否是公共路径
        for (String pattern : this.PUBLIC_PATH) {
            if (this.urlPath.match(path, pattern)) {
                matchLog = "::[public] " + path + " matches " + pattern;
                if (this.LOG) System.out.println(matchLog);
                requiredRole = "public";
                break;
            }
        }
        // 如果不是公共路径，则从高到低试探路径所需的权限，确保低权限路径不会被一步步提权
        // 再检测是否是店长路径
        for (String pattern : this.ADMIN_PATH) {
            if (this.urlPath.match(path, pattern)) {
                matchLog = "::[admin] " + path + " matches " + pattern;
                if (this.LOG) System.out.println(matchLog);
                requiredRole = "admin";
                break;
            }
        }
        // 再检测是否是管理员路径
        for (String pattern : this.MANAGER_PATH) {
            if (this.urlPath.match(path, pattern)) {
                matchLog = "::[manager] " + path + " matches " + pattern;
                if (this.LOG) System.out.println(matchLog);
                requiredRole = "manager";
                break;
            }
        }
        // 再检测是否是用户路径
        for (String pattern : this.CUSTOMER_PATH) {
            if (this.urlPath.match(path, pattern)) {
                matchLog = "::[customer] " + path + " matches " + pattern;
                if (this.LOG) System.out.println(matchLog);
                requiredRole = "customer";
                break;
            }
        }

        // 如果没有匹配到任何路径，说明这个请求不是从以/api为前缀的，
        // 这在api请求中是不会发生的，但是浏览器访问的首页就是/
        if (requiredRole.equals("")) {
            System.out.println("::[error] no role matched for " + path);
            // 给他一点小小的柚子厨震撼
            res.setStatus(721);
            res.setContentType("text/html;charset=UTF-8");
            res.getWriter().println("Ciallo～(∠・ω< )⌒☆");
            return;
        }

        // public 无需登录可直接放行
        if (requiredRole.equals("public")) {
            System.out.printf("%s %s [%s]PASS\n", method, path, requiredRole);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 开始校验jwt以及登陆状态
        String token = req.getHeader("Authorization");
        if (token == null) {
            Res.Error(res, 401, 40101, "未登录");
            return;
        }

        Claim claim = new Claim();
        String err = Token.parse(token, claim);
        if (err != null) {
            Res.Error(res, 401, 40102, err);
            return;
        }

        // 开始校验用户是否存在
        if (!User.hasUser(claim.username)) {
            Res.Error(res, 401, 40103, "用户不存在");
            return;
        }

        // 开始校验用户权限
        User user = new User(claim.username);

        if (this.roleMap.get(requiredRole) <= this.roleMap.get(user.role)) {
            System.out.printf("%s %s [%s]%s => [%s]PASS\n", method, path, user.role, user.username, requiredRole);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.printf("%s %s [%s]%s => [%s]DENY\n", method, path, user.role, user.username, requiredRole);
            Res.Error(res, 403, 40301, "权限不足");
        }
    }
}