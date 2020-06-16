package br.com.fooddelivery.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemOutput {
    private Integer id;
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String note;
}
