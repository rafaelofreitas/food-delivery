package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.filter.DailySalesFilter;

public interface DailySalesReportService {
    byte[] issueDailySales(DailySalesFilter filter, String timeOffset);
}
