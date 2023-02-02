package io.mcm.springbootwithjava.rsextractor;

import io.mcm.springbootwithjava.model.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksExtractor implements ResultSetExtractor<List<Book>> {
    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Book> bookList = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setAuthor(rs.getString("author"));
            book.setIsIssued(rs.getBoolean("isIssued"));
            book.setPublishedDate(rs.getDate("publishedDate"));
            book.setPrice(rs.getDouble("price"));
            bookList.add(book);
        }
        return bookList;
    }
}
