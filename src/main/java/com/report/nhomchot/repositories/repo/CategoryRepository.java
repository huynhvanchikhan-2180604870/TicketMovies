package com.report.nhomchot.repositories.repo;

import com.report.nhomchot.entities.Category;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    public static Specification<Category> search(String name, String sortName, String sortDirect) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"),"%" + name + "%"));
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
