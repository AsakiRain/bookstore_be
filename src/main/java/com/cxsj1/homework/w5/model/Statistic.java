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
            System.out.printf("::create new statistic for %s\n", date.toString());
            this._create(date);
        } else {
            System.out.printf("::get statistic for %s\n", date.toString());
        }
        this._get(date);
    }

    private static java.sql.Date _date(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    private void _create(java.util.Date date) {
        int affectedRows = DB.commit("insert into statistics (date) values (?)", _date(date));
        if (affectedRows != 1) throw new RuntimeException("!!统计记录创建失败: " + _date(date));
    }

    private void _set(Map<String, Object> data) {
        System.out.printf("%s", data.toString());
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
        Map<String, Object> map = DB.queryOne("select * from statistics where date = ?", _date(date));
        if (map.size() == 0) throw new RuntimeException("!!统计记录不存在: " + _date(date));
        this._set(map);
    }

    public static boolean hasStatistic(java.util.Date date) {
        return DB.hasRecord("select * from statistics where date = ?", _date(date));
    }

    public void addDealIncome(int income) {
        int affectedRows = DB.commit("update statistics set deal_income = deal_income + ? where date = ?", income,
                _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void addDealCount() {
        int affectedRows = DB.commit("update statistics set deal_count = deal_count + 1 where date = ?",
                _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void addPurchaseCount() {
        int affectedRows = DB.commit("update statistics set purchase_count = purchase_count + 1 where date = ?",
                _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void addPageView() {
        int affectedRows = DB.commit("update statistics set page_view = page_view + 1 where date = ?",
                _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void addGoodsView() {
        int affectedRows = DB.commit("update statistics set goods_view = goods_view + 1 where date = ?",
                _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void addNewGoodsCount() {
        int affectedRows = DB.commit("update statistics set new_goods_count = new_goods_count + 1 where date = ?",
                _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void updateGoodsCount() {
        int affectedRows = DB.commit("update statistics set goods_count = (select count(*) from stocks) where date = "
                + "?", _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void addTotalView() {
        int affectedRows = DB.commit("update statistics set total_view = total_view + 1 where date = ?",
                _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void addNewUserCount() {
        int affectedRows = DB.commit("update statistics set new_user_count = new_user_count + 1 where date = ?",
                _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }

    public void updateUserCount() {
        int affectedRows =
                DB.commit("update statistics set user_count = (select count(*) from users) where date = " + "?",
                        _date(this.date));
        if (affectedRows != 1) throw new RuntimeException("!!更新统计数据数失败: " + _date(this.date));
    }
}
