package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;
import com.cxsj1.homework.w5.model.Form.PurchaseOrderForm;
import com.cxsj1.homework.w5.model.Form.UpdateOrderForm;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Order {
    public String serial;
    public String username;
    public String cost;
    public String isbn;
    public Short status;
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
                , serial, user.username, stock.cost, stock.isbn, "0");
        if (affectedRows != 1) {
            throw new RuntimeException("Insert failed");
        }
        this._get(serial);
    }

    private void _set(Map<String, Object> data) {
        this.serial = (String) data.get("serial");
        this.username = (String) data.get("username");
        this.cost = (String) data.get("cost");
        this.isbn = (String) data.get("isbn");
        this.status = (Short) data.get("status");
        this.created_at = (Long) data.get("created_at");
        this.updated_at = (Long) data.get("updated_at");
        this.finished_at = (Long) data.get("finished_at");
    }

    private void _get(String serial) {
        Map<String, Object> map = DB.queryOne("select * from orders where serial = ?", serial);
        this._set(map);
    }

    public static boolean hasOrder(String serial) {
        return DB.hasRecord("select * from orders where serial = ?", serial);
    }

    public void set(UpdateOrderForm updateOrderForm) {
        this.status = updateOrderForm.status;
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

    public static HashMap<String, Object> emulate(User user, Stock stock) {
        HashMap<String, Object> orderInfo = new HashMap<>() {
            {
                put("user_info", user);
                put("goods_info", stock);
                put("remain_balance", user.balance - stock.cost);
                put("can_purchase", user.balance >= stock.cost && stock.stock > 0);
            }
        };
        return orderInfo;
    }
}
