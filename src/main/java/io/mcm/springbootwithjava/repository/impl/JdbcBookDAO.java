package io.mcm.springbootwithjava.repository.impl;

import io.mcm.springbootwithjava.model.Book;
import io.mcm.springbootwithjava.repository.BookDAO;
import io.mcm.springbootwithjava.rsextractor.BooksExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcBookDAO implements BookDAO {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBookDAO.class);

    @Override
    public List<Book> add(List<Book> bookList) {
        List<Book> addedBooks = new ArrayList<>();
        for (Book book : bookList) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("id", book.getId());
            parameterMap.put("price", book.getPrice());
            parameterMap.put("author", book.getAuthor());
            parameterMap.put("publishedDate", book.getPublishedDate());
            parameterMap.put("issued", book.getIsIssued());
            String insertStatement = "INSERT INTO books (id, price, publishedDate, author, isIssued) values (:id, :price, :publishedDate, :author, :issued)";
            int rowsUpdated = jdbcTemplate.update(insertStatement, parameterMap);
            if (rowsUpdated > 0) {
                addedBooks.add(book);
            }
        }
        return addedBooks;
    }

    @Override
    public List<Book> findAll() {
        String queryString = "SELECT * FROM learn_kotlin.books";
        return jdbcTemplate.query(queryString, new BooksExtractor());
    }

    @Override
    public Book findById(Long id) {
        String queryString = "SELECT * FROM learn_kotlin.books where id = :id";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("id", id);
        List<Book> bookList = jdbcTemplate.query(queryString, parameterMap, new BooksExtractor());
        if (null == bookList || bookList.size() == 0) {
            return null;
        }
        return bookList.get(0);
    }

    @Override
    public List<Book> update(List<Book> bookList) {
        List<Book> updatedBooks = new ArrayList<>();
        for (Book book : bookList) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("id", book.getId());
            parameterMap.put("author", book.getAuthor());
            parameterMap.put("price", book.getPrice());
            parameterMap.put("isIssued", book.getIsIssued());
            parameterMap.put("publishedDate", book.getPublishedDate());
            String updateStatement = "UPDATE books SET price=:price, author=:author, isIssued=:isIssued, publishedDate=:publishedDate WHERE id=:id";
            int rowsUpdated = jdbcTemplate.update(updateStatement, parameterMap);
            if (rowsUpdated > 0) {
                updatedBooks.add(book);
            }
        }
        return updatedBooks;
    }

    @Override
    public List<Book> delete(List<Long> bookIdList) {
        List<Book> deletedBooks = new ArrayList<>();
        for (Long id : bookIdList) {
            Book book = findById(id);
            String deleteQuery = "DELETE FROM books WHERE id=:id";
            int deletedRowCount = jdbcTemplate.update(deleteQuery, Map.of("id", book.getId()));
            if (deletedRowCount > 0) {
                deletedBooks.add(book);
            }
        }
        return deletedBooks;
    }
}
