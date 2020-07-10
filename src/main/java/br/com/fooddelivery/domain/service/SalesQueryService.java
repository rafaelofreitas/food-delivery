package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesQueryService {
    List<DailySales> consultDailySales(DailySalesFilter dailySalesFilter);
}
