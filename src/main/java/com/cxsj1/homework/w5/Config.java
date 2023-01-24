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
}
