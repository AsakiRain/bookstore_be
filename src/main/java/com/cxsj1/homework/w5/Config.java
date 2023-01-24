package com.cxsj1.homework.w5;

public class Config {

    public static final class JWT {
        public static final String SECRET = "😨世界，遗忘我😭";
        public static final String ISSUER = "rainchen";
    }

    public static final class DB {
        public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
        public static final String URL = "jdbc:mysql://localhost:6033/bookstore?autoReconnect=true&useSSL=false" +
                "&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        public static final String USER = "crud";
        public static final String PASS = "ssr129631";
    }

    public static final class CORS_CONFIG {
        public static final String ALLOW_ORIGIN = "*";
        public static final String ALLOW_METHODS = "GET, POST, PUT, DELETE, OPTIONS";
        public static final String ALLOW_HEADERS = "Content-Type, Authorization";
        public static final String ALLOW_CREDENTIALS = "true";
    }

    public static final class PERMISSION_CONFIG {
        public static final String[] PUBLIC_PATH = {"/", "/login", "/register", "/goods/*", "/book/*"};
        public static final String[] PUBLIC_METHOD = {"OPTIONS"};
        public static final String[] CUSTOMER_PATH = {"/user/*", "/order/*"};
        public static final String[] MANAGER_PATH = {"/user/*", "/order/*", "/manage/*"};
        public static final String[] ADMIN_PATH = {"/user/*", "/order/*", "/manage/*", "/admin/*"};
        public static final boolean VERBOSE = false;
        public static final boolean LOG = false;
        public static final String PREFIX = "/api";
    }
}
