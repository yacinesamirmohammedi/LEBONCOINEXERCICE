package com.example.demo.repository;

import com.example.demo.model.Annonce;
import com.example.demo.model.Categorie;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AnnonceSpecifications {

    public static Specification<Annonce> withFilters(
            BigDecimal prixMin,
            BigDecimal prixMax,
            String titre,
            List<Categorie> categories
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (prixMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("prix"), prixMin));
            }
            if (prixMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("prix"), prixMax));
            }
            if (titre != null && !titre.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("titre")), "%" + titre.toLowerCase() + "%"));
            }
            if (categories != null && !categories.isEmpty()) {
                predicates.add(root.get("categorie").in(categories));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
