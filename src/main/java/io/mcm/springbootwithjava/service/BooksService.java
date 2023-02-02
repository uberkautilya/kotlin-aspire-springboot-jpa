package io.mcm.springbootwithjava.service;

import io.mcm.springbootwithjava.exceptionhandling.exception.BooksException;
import io.mcm.springbootwithjava.model.Book;
import io.mcm.springbootwithjava.model.BooksResponse;
import io.mcm.springbootwithjava.dao.impl.JdbcBookDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BooksService {

    @Autowired
    private JdbcBookDAO jdbcBookRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BooksService.class);

    public BooksResponse addBooks(List<Book> bookList) {
        List<Book> booksAdded = new ArrayList<>();
        try {
            booksAdded = jdbcBookRepository.add(bookList);
        } catch (Exception e) {
            LOGGER.info("BooksService.addBooks: exception: " + e.getMessage());
        }
        List<Long> bookIdsAdded = booksAdded.stream().map(Book::getId).collect(Collectors.toList());
        List<Book> booksNotCreated = new ArrayList<>();
        for (Book book : bookList) {
            if (!bookIdsAdded.contains(book.getId())) {
                booksNotCreated.add(book);
            }
        }
        if (!booksNotCreated.isEmpty()) {
            throw new BooksException(null, null, null, booksNotCreated);
        }
        return new BooksResponse(booksAdded);
    }

    public BooksResponse findAll() {
        List<Book> booksFound = jdbcBookRepository.findAll();
        return new BooksResponse(booksFound);
    }

    public BooksResponse updateBooks(List<Book> bookList) {
        List<Book> booksUpdated = null;
        try {
            booksUpdated = jdbcBookRepository.update(bookList);
        } catch (Exception e) {
            LOGGER.info("BooksService.updateBooks: exception: " + e.getMessage());
        }
        List<Long> updatedBookIdList = booksUpdated.stream().map(Book::getId).collect(Collectors.toList());
        List<Book> booksNotUpdated = new ArrayList<>();
        for (Book book : bookList) {
            if (!updatedBookIdList.contains(book.getId())) {
                booksNotUpdated.add(book);
            }
        }
        if (!booksNotUpdated.isEmpty()) {
            throw new BooksException(null, null, booksNotUpdated, null);
        }
        return new BooksResponse(booksUpdated);
    }

    public BooksResponse deleteBooks(List<Long> bookIdsToDeleteList) {
        List<Book> deletedBookList = new ArrayList<>();
        try {
            deletedBookList = jdbcBookRepository.delete(bookIdsToDeleteList);
        } catch (Exception e) {
            LOGGER.info("BooksService.deleteBooks: exception: " + e.getMessage());
        }
        List<Long> deletedBookIds = deletedBookList.stream().map(Book::getId).collect(Collectors.toList());
        List<Long> bookIdsNotDeleted = new ArrayList<>();
        for (Long id : bookIdsToDeleteList) {
            if (!deletedBookIds.contains(id)) {
                bookIdsNotDeleted.add(id);
            }
        }
        if (!bookIdsNotDeleted.isEmpty()) {
            throw new BooksException(bookIdsNotDeleted, null, null, null);
        }
        return new BooksResponse(deletedBookList);
    }

    public BooksResponse findById(Long id) {
        Book bookById = jdbcBookRepository.findById(id);
        if (Objects.isNull(bookById)) {
            throw new BooksException(null, List.of(id), null, null);
        }
        return new BooksResponse(List.of(bookById));
    }
}
