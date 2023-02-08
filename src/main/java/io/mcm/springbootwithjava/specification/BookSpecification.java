package io.mcm.springbootwithjava.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import io.mcm.springbootwithjava.model.entities.Book;
import io.mcm.springbootwithjava.model.BooksRequest;
import org.springframework.util.CollectionUtils;

@Component
public class BookSpecification {

    public Specification<Book> getBooks(BooksRequest request) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            Book bookFilter;
            if (null != request && CollectionUtils.isEmpty(request.getBookList())) {
                bookFilter = request.getBookList().get(0);
            } else {
                System.out.println("The request book parameters are not provided");
                return null;
            }

            if (bookFilter.getIsIssued() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isIssued"), bookFilter.getIsIssued()));
            }
            if (bookFilter.getAuthor() != null && !bookFilter.getAuthor().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")),
                        "%" + bookFilter.getAuthor().toLowerCase() + "%"));
            }
            if (bookFilter.getPrice() != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), bookFilter.getPrice()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("publishedDate")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}