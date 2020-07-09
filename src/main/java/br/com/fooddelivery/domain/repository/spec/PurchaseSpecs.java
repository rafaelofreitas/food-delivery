package br.com.fooddelivery.domain.repository.spec;

import br.com.fooddelivery.domain.model.Purchase;
import br.com.fooddelivery.domain.repository.filter.PurchaseFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseSpecs {
    public static Specification<Purchase> usingFilter(PurchaseFilter filter) {
        return (root, query, builder) -> {
            root.fetch("restaurant").fetch("kitchen");
            root.fetch("client");

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("client"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getCreationDateIni() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getCreationDateIni()));
            }

            if (filter.getCreationDateEnd() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getCreationDateEnd()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
