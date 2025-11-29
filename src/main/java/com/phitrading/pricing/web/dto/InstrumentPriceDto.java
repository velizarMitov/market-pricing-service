package com.phitrading.pricing.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO representing an instrument basic information and latest pricing.
 */
public class InstrumentPriceDto {

    private UUID id;
    private String symbol;
    private String name;
    private BigDecimal lastPrice;
    private BigDecimal previousClose;
    private Instant updatedAt;

    public InstrumentPriceDto() {
    }

    public InstrumentPriceDto(UUID id, String symbol, String name,
                              BigDecimal lastPrice, BigDecimal previousClose,
                              Instant updatedAt) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.lastPrice = lastPrice;
        this.previousClose = previousClose;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
