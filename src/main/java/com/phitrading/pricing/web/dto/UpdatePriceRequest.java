package com.phitrading.pricing.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Request payload to update the last price of an instrument.
 */
public class UpdatePriceRequest {

    @NotNull
    @Positive
    private BigDecimal newPrice;

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }
}
