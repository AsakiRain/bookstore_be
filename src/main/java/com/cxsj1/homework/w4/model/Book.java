package com.cxsj1.homework.w4.model;

import com.cxsj1.homework.w4.database.DB;

import java.util.Map;

public class Book {
    public String isbn;
    public String title;
    public String author;
    public String publisher;
    public String intro;
    public String cover;
    public String price;

    public void set(BookForm bookForm) {
        this.isbn = bookForm.isbn;
        this.title = bookForm.title;
        this.author = bookForm.author;
        this.publisher = bookForm.publisher;
        this.intro = bookForm.intro;
        this.cover = bookForm.cover;
        this.price = bookForm.price;
    }

    public void set(Map<String, Object> item) {
        this.isbn = (String) item.get("isbn");
        this.title = (String) item.get("title");
        this.author = (String) item.get("author");
        this.publisher = (String) item.get("publisher");
        this.intro = (String) item.get("intro");
        this.cover = (String) item.get("cover");
        this.price = (String) item.get("price");
    }

    public String get(String isbn) {
        Map<String, Object> map = DB.queryOne("select * from books where isbn = ?", isbn);
        if (map == null) {
            return "图书不存在";
        }
        this.isbn = (String) map.get("isbn");
        this.title = (String) map.get("title");
        this.author = (String) map.get("author");
        this.publisher = (String) map.get("publisher");
        this.intro = (String) map.get("intro");
        this.cover = (String) map.get("cover");
        this.price = (String) map.get("price");
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
        int affectedRows = DB.commit("update books set title = ?, author = ?, publisher = ?, intro = ?, cover = ?, " +
                "price = ? where isbn = ?", this.title, this.author, this.publisher, this.intro, this.cover,
                this.price, this.isbn);
        return affectedRows == 1;
    }

    public static boolean create(BookForm bookForm) {
        int affectedRows = DB.commit("insert into books (isbn, title, author, publisher, intro, cover, price) values " +
                "(?, ?, ?, ?, ?, ?, ?)", bookForm.isbn, bookForm.title, bookForm.author, bookForm.publisher,
                bookForm.intro, bookForm.cover, bookForm.price);
        return affectedRows == 1;
    }
}
