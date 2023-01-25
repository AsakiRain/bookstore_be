package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;
import com.cxsj1.homework.w5.model.Form.ProcessOrderForm;

import java.util.*;

public class Order {
    public String serial;
    public String username;
    public Integer cost;
    public String isbn;
    public Integer status;
    public Long created_at;
    public Long updated_at;
    public Long finished_at;

    public Order(String serial) {
        if (!hasOrder(serial)) {
            throw new RuntimeException("No such order");
        }
        this._get(serial);
    }

    public Order(User user, Stock stock) {
        String serial = String.valueOf(UUID.randomUUID());
        int affectedRows = DB.commit("insert into orders(serial, username, cost, isbn, status) values(?, ?, ?, ?, ?)"
                , serial, user.username, stock.cost, stock.isbn, 0);
        if (affectedRows != 1) {
            throw new RuntimeException("Insert failed");
        }
        this._get(serial);
    }

    public Order(Map<String, Object> data) {
        this._set(data);
    }

    private void _set(Map<String, Object> data) {
        this.serial = (String) data.get("serial");
        this.username = (String) data.get("username");
        this.cost = (Integer) data.get("cost");
        this.isbn = (String) data.get("isbn");
        this.status = (Integer) data.get("status");
        this.created_at = ((Date) data.get("created_at")).getTime();
        this.updated_at = ((Date) data.get("updated_at")).getTime();
        this.finished_at = data.get("finished_at") == null ? null : ((Date) data.get("finished_at")).getTime();
    }

    private void _get(String serial) {
        Map<String, Object> map = DB.queryOne("select * from orders where serial = ?", serial);
        this._set(map);
    }

    public static boolean hasOrder(String serial) {
        return DB.hasRecord("select * from orders where serial = ?", serial);
    }

    public void set(ProcessOrderForm processOrderForm) {
        this.status = processOrderForm.status;
    }

    public void save() {
        int affectedRows;
        if (this.status == 3) {
            affectedRows = DB.commit("update orders set status = ?, finished_at = CURRENT_TIMESTAMP where serial = ?"
                    , this.status, this.serial);
        } else {
            affectedRows = DB.commit("update orders set status = ? where serial = ?", this.status, this.serial);
        }
        if (affectedRows != 1) {
            throw new RuntimeException("Update failed");
        }
    }

    public static int countList(String username) {
        return DB.countBy("SELECT COUNT(*) FROM orders WHERE username = ?", username);
    }

    public static ArrayList<Order> list(int page, String username) {
        ArrayList<HashMap<String, Object>> list = DB.queryAll("SELECT * FROM orders WHERE username = ? LIMIT ?, 20",
                username, (page - 1) * 20);
        ArrayList<Order> orders = new ArrayList<>();
        for (HashMap<String, Object> item : list) {
            Order order = new Order(item);
            orders.add(order);
        }
        return orders;
    }

    public static int manage_countList() {
        return DB.countBy("SELECT COUNT(*) FROM orders");
    }

    public static ArrayList<Order> manage_list(int page) {
        ArrayList<HashMap<String, Object>> list = DB.queryAll("SELECT * FROM orders LIMIT ?, 20", (page - 1) * 20);
        ArrayList<Order> orders = new ArrayList<>();
        for (HashMap<String, Object> item : list) {
            Order order = new Order(item);
            orders.add(order);
        }
        return orders;
    }


}
