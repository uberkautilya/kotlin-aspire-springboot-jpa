package io.mcm.springbootwithjava.repository;

import io.mcm.springbootwithjava.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    public List<Book> findByPriceAndPublishedDateAndAuthorAndIsIssued(
            @Param("price") Double price,
            @Param("publishedDate") Date publishedDate,
            @Param("author") String author,
            @Param("isIssues") Boolean isIssued);
}
