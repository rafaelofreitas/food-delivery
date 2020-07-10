package br.com.fooddelivery.domain.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class DailySales {
    private LocalDate date;
    private Integer salesAmount;
    private BigDecimal totalBilled;
}
