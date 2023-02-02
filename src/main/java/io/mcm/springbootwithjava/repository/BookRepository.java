package io.mcm.springbootwithjava.repository;

import io.mcm.springbootwithjava.model.Book;

import java.util.List;

public interface BookRepository {
    public List<Book> add(List<Book> bookList);

    public List<Book> findAll();

    public Book findById(Long id);

    public List<Book> update(List<Book> bookList);

    public List<Book> delete(List<Long> bookIdList);
}
