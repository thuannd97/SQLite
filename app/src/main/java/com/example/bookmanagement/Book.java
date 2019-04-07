package com.example.bookmanagement;

import java.io.Serializable;

public class Book implements Serializable {

    private int id;
    private String bookName;
    private String author;
    private String category;

    public Book(int id, String bookName, String author, String category) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.category = category;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
