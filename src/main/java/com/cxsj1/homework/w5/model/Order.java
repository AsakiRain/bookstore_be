package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;
import com.cxsj1.homework.w5.model.Form.UpdateOrderForm;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
}
