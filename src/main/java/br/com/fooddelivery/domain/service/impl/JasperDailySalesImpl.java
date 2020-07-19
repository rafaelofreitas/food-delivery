package br.com.fooddelivery.domain.service.impl;

import br.com.fooddelivery.domain.exception.ReportException;
import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.service.DailySalesReportService;
import br.com.fooddelivery.domain.service.SalesQueryService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class JasperDailySalesImpl implements DailySalesReportService {
    private final SalesQueryService salesQueryService;

    public JasperDailySalesImpl(SalesQueryService salesQueryService) {
        this.salesQueryService = salesQueryService;
    }

    @Override
    public byte[] issueDailySales(DailySalesFilter filter, String timeOffset) {
        try {
            var inputStream = this.getClass().getResourceAsStream("/reports/daily_sales.jasper");

            var params = new HashMap<String, Object>();
            params.put("REPORT_LOCALE", new Locale("pr", "BR"));

            var dataSource = new JRBeanCollectionDataSource(this.salesQueryService.consultDailySales(filter, timeOffset));

            var jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException("It was not possible to issue the sales report!");
        }
    }
}
