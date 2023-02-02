package io.mcm.springbootwithjava.model;

import java.util.List;

public class BooksResponse {
    List<Book> bookList;

    public BooksResponse(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "BooksResponse{" +
                "bookList=" + bookList +
                '}';
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
