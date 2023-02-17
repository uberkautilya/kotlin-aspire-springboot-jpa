package io.mcm.springbootwithjava.model;

import io.mcm.springbootwithjava.model.entities.Book;

public class BookFilter extends Book {
    String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
