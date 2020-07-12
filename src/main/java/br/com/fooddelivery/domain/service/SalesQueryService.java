package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import br.com.fooddelivery.domain.repository.SalesQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesQueryService {
    private final SalesQueryRepository salesQueryRepository;

    public SalesQueryService(SalesQueryRepository salesQueryRepository) {
        this.salesQueryRepository = salesQueryRepository;
    }

    public List<DailySales> consultDailySales(DailySalesFilter filter, String timeOffset) {
        return this.salesQueryRepository.consultDailySales(filter, timeOffset);
    }
}
