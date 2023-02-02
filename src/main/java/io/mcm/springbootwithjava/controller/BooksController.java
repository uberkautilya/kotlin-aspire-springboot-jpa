package io.mcm.springbootwithjava.controller;

import io.mcm.springbootwithjava.model.BooksRequest;
import io.mcm.springbootwithjava.model.BooksResponse;
import io.mcm.springbootwithjava.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController("books")
public class BooksController {
    @Autowired
    BooksService booksService;

    public ResponseEntity<BooksResponse> addBooks(@RequestBody BooksRequest booksRequest) {
        BooksResponse booksResponse = booksService.addBooks(booksRequest.getBookList());
        return ResponseEntity.ok(booksResponse);
    }

    public ResponseEntity<BooksResponse> findAll() {
        BooksResponse booksFound = booksService.findAll();
        return new ResponseEntity<>(booksFound, HttpStatus.OK);
    }

    public ResponseEntity<BooksResponse> findById(@PathParam("id") Long id) {
        BooksResponse bookById = booksService.findById(id);
        return new ResponseEntity<>(bookById, HttpStatus.OK);
    }

    public ResponseEntity<BooksResponse> updateBooks(@RequestBody BooksRequest booksRequest) {
        BooksResponse updatedBooksResponse = booksService.updateBooks(booksRequest.getBookList());
        return new ResponseEntity<>(updatedBooksResponse, HttpStatus.OK);
    }

    public ResponseEntity<BooksResponse> deleteBooks(@RequestBody List<Long> bookIdsToDelete) {
        BooksResponse booksDeletedResponse = booksService.deleteBooks(bookIdsToDelete);
        return new ResponseEntity<>(booksDeletedResponse, HttpStatus.OK);
    }

}
