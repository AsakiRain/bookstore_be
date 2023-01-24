package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;

import java.util.HashMap;
import java.util.ArrayList;

public class Goods {
    public static int countList() {
        return DB.countBy("SELECT COUNT(*) FROM stocks WHERE for_sale = ?", 1);
    }

    public static ArrayList<Stock> list(int page) {
        ArrayList<HashMap<String, Object>> list = DB.queryAll("SELECT * FROM stocks WHERE for_sale = ? LIMIT ?, 20",
                1, (page - 1) * 20);
        ArrayList<Stock> goods = new ArrayList<>();
        for (HashMap<String, Object> item : list) {
            Stock stock = new Stock(item);
            goods.add(stock);
        }
        return goods;
    }
    public static  int countSearch(String keyword) {
        return DB.countBy("SELECT COUNT(*) FROM stocks WHERE for_sale = ? AND name LIKE ?", 1, "%" + keyword + "%");
    }
    public static ArrayList<Stock> search(String keyword, int page) {
        ArrayList<HashMap<String, Object>> list = DB.queryAll("SELECT * FROM stocks WHERE for_sale = ? AND name LIKE "
                + "? LIMIT ?, 20", 1, "%" + keyword + "%", (page - 1) * 20);
        ArrayList<Stock> goods = new ArrayList<>();
        for (HashMap<String, Object> item : list) {
            Stock stock = new Stock(item);
            goods.add(stock);
        }
        return goods;
    }
}

