package com.cxsj1.homework.w4.model;

import com.cxsj1.homework.w4.database.DB;

import java.util.*;

public class BookList {
    public String username;
    public int count;

    public BookList(String username) {
        this.username = username;
        this.count = DB.countBy("select count(*) from lists where username = ?", username);
    }

    public ArrayList<Book> list(int page) {
        List<HashMap<String, Object>> list = DB.queryAll(("SELECT b.* FROM lists AS l INNER JOIN books b ON l.isbn = b" +
                ".isbn" +
                " WHERE username = ? LIMIT ").concat(String.valueOf((page - 1) * 20)).concat(", 20"), this.username);

        ArrayList<Book> books = new ArrayList<>();
        for (Map<String, Object> item : list) {
            Book book = new Book();
            book.set(item);
            books.add(book);
        }
        return books;
    }

    public ArrayList<Book> search(String keyword) {
        List<HashMap<String, Object>> list = DB.queryAll(("SELECT b.* FROM lists AS l INNER JOIN books b ON l.isbn = b" +
                ".isbn" +
                " WHERE username = ? AND b.title LIKE ?"), this.username, "%" + keyword + "%");

        ArrayList<Book> books = new ArrayList<>();
        for (HashMap<String, Object> item : list) {
            Book book = new Book();
            book.set(item);
            books.add(book);
        }
        return books;
    }

    public boolean add(String isbn) {
        int affectedRows = DB.commit("INSERT INTO lists (username, isbn) VALUES (?, ?)", this.username, isbn);
        return affectedRows == 1;
    }

    public boolean delete(String isbn) {
        int affectedRows = DB.commit("DELETE FROM lists WHERE username = ? AND isbn = ?", this.username, isbn);
        return affectedRows == 1;
    }
}
