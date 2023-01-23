package com.cxsj1.homework.w5.model;

import com.cxsj1.homework.w5.database.DB;
import com.cxsj1.homework.w5.model.Form.BookForm;

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

    public void set(BookForm bookForm) {
        this.isbn = bookForm.isbn;
        this.title = bookForm.title;
        this.author = bookForm.author;
        this.publisher = bookForm.publisher;
        this.publish_at = bookForm.publish_at;
        this.page = bookForm.page;
        this.binding = bookForm.binding;
        this.series = bookForm.series;
        this.translator = bookForm.translator;
        this.original_title = bookForm.original_title;
        this.producer = bookForm.producer;
        this.id = bookForm.id;
        this.url = bookForm.url;
        this.rating = bookForm.rating;
        this.rating_people = bookForm.rating_people;
        this.intro = bookForm.intro;
        this.cover = bookForm.cover;
        this.price = bookForm.price;
        this.cost = bookForm.cost;
        this.stock = bookForm.stock;
        this.for_sale = bookForm.for_sale;
    }

    public void set(Map<String, Object> data) {
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

    public String get(String isbn) {
        Map<String, Object> map = DB.queryOne("select * from books where isbn = ?", isbn);
        if (map.size() == 0) {
            return "图书不存在";
        }
        this.set(map);
        return null;
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
                DB.commit("update books set title = ?, author = ?, publisher = ?, publish_at = ?, page = ?, binding =" +
                        " ?, series = ?, translator = ?, original_title = ?, producer = ?, id = ?, url = " + "?, " +
                        "rating = ?, rating_people = ?, intro = ?, cover = ?, price = ?, cost = ?, stock = ?, " +
                        "for_sale = ? where isbn = ?", this.title, this.author, this.publisher, this.publish_at,
                        this.page, this.binding, this.series, this.translator, this.original_title, this.producer,
                        this.id, this.url, this.rating, this.rating_people, this.intro, this.cover, this.price,
                        this.cost, this.stock, this.for_sale, this.isbn);
        return affectedRows == 1;
    }

    public static boolean create(BookForm bookForm) {
        int affectedRows =
                DB.commit("insert into books (isbn, title, author, publisher, publish_at, page, binding, series, " +
                        "translator, original_title, producer, id, url, rating, rating_people, intro, cover, price, " +
                        "cost, stock, for_sale) " + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                        " ?, ?)", bookForm.isbn, bookForm.title, bookForm.author, bookForm.publisher,
                        bookForm.publish_at, bookForm.page, bookForm.binding, bookForm.series, bookForm.translator,
                        bookForm.original_title, bookForm.producer, bookForm.id, bookForm.url, bookForm.rating,
                        bookForm.rating_people, bookForm.intro, bookForm.cover, bookForm.price, bookForm.cost,
                        bookForm.stock, bookForm.for_sale);
        return affectedRows == 1;
    }
}
