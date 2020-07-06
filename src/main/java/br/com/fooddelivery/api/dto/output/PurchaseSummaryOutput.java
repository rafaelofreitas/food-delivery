package br.com.fooddelivery.api.dto.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/*  @JsonFilter("purchaseFilter")
    example: Limiting the fields returned by the API with Jackson's @JsonFilter
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PurchaseSummaryOutput {
    private UUID purchaseCode;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal amount;
    private OffsetDateTime creationDate;
    private RestaurantSummaryOutput restaurant;
    private UserOutput client;
}
