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

    public void set(Map<String, Object> item) {
        this.isbn = (String) item.get("isbn");
        this.title = (String) item.get("title");
        this.author = (String) item.get("author");
        this.publisher = (String) item.get("publisher");
        this.publish_at = (String) item.get("publish_at");
        this.page = (String) item.get("page");
        this.binding = (String) item.get("binding");
        this.series = (String) item.get("series");
        this.translator = (String) item.get("translator");
        this.original_title = (String) item.get("original_title");
        this.producer = (String) item.get("producer");
        this.id = (String) item.get("id");
        this.url = (String) item.get("url");
        this.rating = (String) item.get("rating");
        this.rating_people = (String) item.get("rating_people");
        this.intro = (String) item.get("intro");
        this.cover = (String) item.get("cover");
        this.price = (String) item.get("price");
        this.cost = (String) item.get("cost");
        this.stock = (String) item.get("stock");
        this.for_sale = (String) item.get("for_sale");
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
