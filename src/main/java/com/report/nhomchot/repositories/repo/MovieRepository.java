package com.report.nhomchot.repositories.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.report.nhomchot.entities.Movie;

import jakarta.persistence.criteria.Predicate;

public class MovieRepository {
    public static Specification<Movie> search(String name, String sortName, String sortDirect) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + name + "%"));
            }
            if (sortName != null && !sortName.isEmpty()) {
                if (sortDirect.equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(root.get(sortName)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(sortName)));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
