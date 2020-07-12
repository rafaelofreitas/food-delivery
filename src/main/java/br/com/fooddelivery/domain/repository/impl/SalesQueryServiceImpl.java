package br.com.fooddelivery.domain.repository.impl;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.OrderStatus;
import br.com.fooddelivery.domain.model.Purchase;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import br.com.fooddelivery.domain.repository.SalesQueryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SalesQueryServiceImpl implements SalesQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DailySales> consultDailySales(DailySalesFilter filter, String timeOffset) {
        var builder = this.entityManager.getCriteriaBuilder();
        var query = builder.createQuery(DailySales.class);
        var root = query.from(Purchase.class);
        List<Predicate> predicates = new ArrayList<>();

        var functionConvertTzCreationDate = builder.function(
                "convert_tz",
                Date.class,
                root.get("creationDate"),
                builder.literal("+00:00"),
                builder.literal(timeOffset));

        var functionDateCreationDate = builder.function("date", Date.class, functionConvertTzCreationDate);

        var selection = builder.construct(
                DailySales.class,
                functionDateCreationDate,
                builder.count(root.get("id")),
                builder.sum(root.get("subtotal"))
        );

        if (filter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
        }

        if (filter.getCreationDateIni() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getCreationDateIni()));
        }

        if (filter.getCreationDateEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getCreationDateEnd()));
        }

        predicates.add(root.get("orderStatus").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateCreationDate);

        return this.entityManager.createQuery(query).getResultList();
    }
}
