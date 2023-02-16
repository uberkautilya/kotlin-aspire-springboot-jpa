package io.mcm.springbootwithjava.specification;

import io.mcm.springbootwithjava.model.BooksRequest;
import io.mcm.springbootwithjava.model.entities.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookSpecification {

    public Specification<Book> getBooks(BooksRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Book bookFilter;
            if (Objects.nonNull(request) || Objects.nonNull(request.getBookFilter())) {
                bookFilter = request.getBookFilter();
            } else {
                System.out.println("The request book parameters are not provided");
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
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