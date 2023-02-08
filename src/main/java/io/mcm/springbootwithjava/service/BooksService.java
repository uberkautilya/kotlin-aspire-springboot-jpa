package io.mcm.springbootwithjava.service;

import io.mcm.springbootwithjava.exceptionhandling.exception.BooksException;
import io.mcm.springbootwithjava.model.entities.Book;
import io.mcm.springbootwithjava.model.BooksResponse;
import io.mcm.springbootwithjava.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {

    @Autowired
    private BookRepository bookRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BooksService.class);

    public BooksResponse save(List<Book> bookList) {
        List<Book> booksAdded = new ArrayList<>();
        try {
            booksAdded = bookRepository.saveAll(bookList);
        } catch (Exception e) {
            LOGGER.info("BooksService.addBooks: exception: " + e.getMessage());
            throw new BooksException(null, null, null, bookList);
        }
        return new BooksResponse(booksAdded);
    }

    public BooksResponse findAll() {
        List<Book> booksFound = bookRepository.findAll();
        return new BooksResponse(booksFound);
    }

    public BooksResponse updateBooks(List<Book> bookList) {
        List<Book> booksUpdated = null;
        try {
            booksUpdated = bookRepository.saveAll(bookList);
        } catch (Exception e) {
            LOGGER.info("BooksService.updateBooks: exception: " + e.getMessage());
            throw new BooksException(null, null, bookList, null);
        }
        return new BooksResponse(booksUpdated);
    }

    public BooksResponse deleteBooks(List<Long> bookIdsToDeleteList) {
        List<Book> deletedBookList = new ArrayList<>();
        for (Long id : bookIdsToDeleteList) {
            Optional<Book> bookOptional = bookRepository.findById(id);
            bookOptional.ifPresent(b -> deletedBookList.add(b));
        }
        try {
            bookRepository.deleteAllById(bookIdsToDeleteList);
        } catch (Exception e) {
            LOGGER.info("BooksService.deleteBooks: exception: " + e.getMessage());
            throw new BooksException(bookIdsToDeleteList, null, null, null);
        }
        return new BooksResponse(deletedBookList);
    }

    public BooksResponse findById(Long id) {
        Optional<Book> bookById = bookRepository.findById(id);
        if (bookById.isEmpty()) {
            throw new BooksException(null, List.of(id), null, null);
        }
        BooksResponse booksResponse = new BooksResponse(List.of(bookById.get()));
        return booksResponse;
    }
}
