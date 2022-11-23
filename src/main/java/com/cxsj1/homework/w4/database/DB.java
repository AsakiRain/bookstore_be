package com.cxsj1.homework.w4.database;

import com.cxsj1.homework.w4.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {


    static {
        try {
            Class.forName(Config.DB.DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败");
            e.printStackTrace();
        }
    }

    private static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Config.DB.URL, Config.DB.USER, Config.DB.PASS);
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
        return conn;
    }

    public static int commit(String sql, Object... params) {
        Connection conn = getConn();
        PreparedStatement ps = null;
        int affectedRows;
        try {
            ps = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            affectedRows = 0;
            e.printStackTrace();
        } finally {
            release(conn, ps, null);
        }
        return affectedRows;
    }

    public static List<Map<String, Object>> queryAll(String sql, Object... params) {
        Connection conn = getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(conn, ps, rs);
        }
        return list;
    }

    public static Map<String, Object> queryOne(String sql, Object... params) {
        Connection conn = getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = null;
        try {
            ps = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            map = new HashMap<>();
            if (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(conn, ps, rs);
        }
        return map;
    }

    public static int getRowCount(String dbName) {
        Connection conn = getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowCount = 0;
        try {
            ps = conn.prepareStatement("select COUNT(*) as row_count from " + dbName);
            rs = ps.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(conn, ps, rs);
        }
        return rowCount;
    }

    public static boolean hasRecord(String sql, Object... params) {
        Connection conn = getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean hasRecord = false;
        try {
            ps = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            hasRecord = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(conn, ps, rs);
        }
        return hasRecord;
    }

    private static void release(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
