package io.mcm.springbootwithjava.model;

import java.util.List;

public class BooksRequest {
    List<Book> bookList;

    public BooksRequest() {
    }

    public BooksRequest(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
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
