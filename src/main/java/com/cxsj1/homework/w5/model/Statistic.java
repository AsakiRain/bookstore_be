package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;

import java.util.Date;
import java.util.Map;

public class Statistic {
    public java.util.Date date;
    public int deal_income;
    public int deal_count;
    public int purchase_count;
    public int page_view;
    public int goods_view;
    public int new_goods_count;
    public int goods_count;
    public int total_view;
    public int new_user_count;
    public int user_count;
    public Long created_at;
    public Long updated_at;

    public Statistic(java.util.Date date) {
        if (!hasStatistic(date)) {
            this._create(date);
        }
        this._get(date);
    }

    private boolean _create(java.util.Date date) {
        int affectedRows = DB.commit("insert into statistics (date) values (?)", date);
        return affectedRows == 1;
    }

    private void _set(Map<String, Object> data) {
        this.date = (java.util.Date) data.get("date");
        this.deal_income = (int) data.get("deal_income");
        this.deal_count = (int) data.get("deal_count");
        this.purchase_count = (int) data.get("purchase_count");
        this.page_view = (int) data.get("page_view");
        this.goods_view = (int) data.get("goods_view");
        this.new_goods_count = (int) data.get("new_goods_count");
        this.goods_count = (int) data.get("goods_count");
        this.total_view = (int) data.get("total_view");
        this.new_user_count = (int) data.get("new_user_count");
        this.user_count = (int) data.get("user_count");
        this.created_at = ((Date) data.get("created_at")).getTime();
        this.updated_at = ((Date) data.get("updated_at")).getTime();
    }

    private void _get(java.util.Date date) {
        Map<String, Object> map = DB.queryOne("select * from statistics where date = ?", date);
        this._set(map);
    }

    public static boolean hasStatistic(java.util.Date date) {
        return DB.hasRecord("select * from statistics where date = ?", date);
    }

    public boolean addDealIncome(int income) {
        int affectedRows = DB.commit("update statistics set deal_income = deal_income + ? where date = ?", income,
                this.date);
        return affectedRows == 1;
    }

    public boolean addDealCount() {
        int affectedRows = DB.commit("update statistics set deal_count = deal_count + 1 where date = ?", this.date);
        return affectedRows == 1;
    }

    public boolean addPurchaseCount() {
        int affectedRows = DB.commit("update statistics set purchase_count = purchase_count + 1 where date = ?",
                this.date);
        return affectedRows == 1;
    }

    public boolean addPageView() {
        int affectedRows = DB.commit("update statistics set page_view = page_view + 1 where date = ?", this.date);
        return affectedRows == 1;
    }

    public boolean addGoodsView() {
        int affectedRows = DB.commit("update statistics set goods_view = goods_view + 1 where date = ?", this.date);
        return affectedRows == 1;
    }

    public boolean addNewGoodsCount() {
        int affectedRows = DB.commit("update statistics set new_goods_count = new_goods_count + 1 where date = ?",
                this.date);
        return affectedRows == 1;
    }

    public boolean updateGoodsCount() {
        int affectedRows =
                DB.commit("update statistics set goods_count = (select count(*) from goods) where date = " + "?",
                        this.date);
        return affectedRows == 1;
    }

    public boolean addTotalView() {
        int affectedRows = DB.commit("update statistics set total_view = total_view + 1 where date = ?", this.date);
        return affectedRows == 1;
    }

    public boolean addNewUserCount() {
        int affectedRows = DB.commit("update statistics set new_user_count = new_user_count + 1 where date = ?",
                this.date);
        return affectedRows == 1;
    }

    public boolean updateUserCount() {
        int affectedRows =
                DB.commit("update statistics set user_count = (select count(*) from users) where date = " + "?",
                        this.date);
        return affectedRows == 1;
    }
}
