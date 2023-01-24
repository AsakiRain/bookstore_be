package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;
import com.cxsj1.homework.w5.model.Form.StockForm;

import java.util.Map;

public class Stock {
    public String isbn;
    public String title;
    public String author;
    public String publisher;
    public String publish_at;
    public String page;
    public String binding;
    public String series;
    public String translator;
    public String original_title;
    public String producer;
    public String id;
    public String url;
    public String rating;
    public String rating_people;
    public String intro;
    public String cover;
    public String price;
    public String cost;
    public String stock;
    public String for_sale;

    public Stock(String isbn) {
        if (!hasBook(isbn)) {
            throw new RuntimeException("No such book");
        }
        this._get(isbn);
    }

    public Stock(StockForm stockForm) {
        int affectedRows =
                DB.commit("insert into books(isbn, title, author, publisher, publish_at, page, binding, series, " +
                                "translator, original_title, producer, id, url, rating, rating_people, intro, cover, " +
                                "price, cost, stock, for_sale) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?)", stockForm.isbn, stockForm.title, stockForm.author, stockForm.publisher,
                        stockForm.publish_at, stockForm.page, stockForm.binding, stockForm.series, stockForm.translator,
                        stockForm.original_title, stockForm.producer, stockForm.id, stockForm.url, stockForm.rating,
                        stockForm.rating_people, stockForm.intro, stockForm.cover, stockForm.price, stockForm.cost,
                        stockForm.stock, stockForm.for_sale);
        if (affectedRows == 1) {
            this._get(stockForm.isbn);
        } else {
            throw new RuntimeException("Insert failed");
        }
    }

    public void set(StockForm stockForm) {
        this.isbn = stockForm.isbn;
        this.title = stockForm.title;
        this.author = stockForm.author;
        this.publisher = stockForm.publisher;
        this.publish_at = stockForm.publish_at;
        this.page = stockForm.page;
        this.binding = stockForm.binding;
        this.series = stockForm.series;
        this.translator = stockForm.translator;
        this.original_title = stockForm.original_title;
        this.producer = stockForm.producer;
        this.id = stockForm.id;
        this.url = stockForm.url;
        this.rating = stockForm.rating;
        this.rating_people = stockForm.rating_people;
        this.intro = stockForm.intro;
        this.cover = stockForm.cover;
        this.price = stockForm.price;
        this.cost = stockForm.cost;
        this.stock = stockForm.stock;
        this.for_sale = stockForm.for_sale;
    }

    private void _set(Map<String, Object> data) {
        this.isbn = (String) data.get("isbn");
        this.title = (String) data.get("title");
        this.author = (String) data.get("author");
        this.publisher = (String) data.get("publisher");
        this.publish_at = (String) data.get("publish_at");
        this.page = (String) data.get("page");
        this.binding = (String) data.get("binding");
        this.series = (String) data.get("series");
        this.translator = (String) data.get("translator");
        this.original_title = (String) data.get("original_title");
        this.producer = (String) data.get("producer");
        this.id = (String) data.get("id");
        this.url = (String) data.get("url");
        this.rating = (String) data.get("rating");
        this.rating_people = (String) data.get("rating_people");
        this.intro = (String) data.get("intro");
        this.cover = (String) data.get("cover");
        this.price = (String) data.get("price");
        this.cost = (String) data.get("cost");
        this.stock = (String) data.get("stock");
        this.for_sale = (String) data.get("for_sale");
    }

    private void _get(String isbn) {
        Map<String, Object> map = DB.queryOne("select * from books where isbn = ?", isbn);
        this._set(map);
    }

    public static boolean hasBook(String isbn) {
        return DB.hasRecord("select * from books where isbn = ?", isbn);
    }

    public static boolean delete(String isbn) {
        int affectedRows = DB.commit("delete from books where isbn = ?", isbn);
        return affectedRows == 1;
    }

    public boolean save() {
        int affectedRows =
                DB.commit("update books set title = ?, author = ?, publisher = ?, publish_at = ?, page = " + "?, " +
                        "binding =" + " ?, series = ?, translator = ?, original_title = ?, producer = ?, id " + "=" + " ?," + " url " + "= " + "?, " + "rating = ?, rating_people = ?, intro = ?, cover = ?, price " + "= ?, " + "cost " + "= ?, " + "stock = ?," + " " + "for_sale = ? where isbn = ?", this.title, this.author, this.publisher, this.publish_at, this.page, this.binding, this.series, this.translator, this.original_title, this.producer, this.id, this.url, this.rating, this.rating_people, this.intro, this.cover, this.price, this.cost, this.stock, this.for_sale, this.isbn);
        return affectedRows == 1;
    }
}
