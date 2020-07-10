package br.com.fooddelivery.domain.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class DailySales {
    private Date date;
    private Long salesAmount;
    private BigDecimal totalBilled;
}
