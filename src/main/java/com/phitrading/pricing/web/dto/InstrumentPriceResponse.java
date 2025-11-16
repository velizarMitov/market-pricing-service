package com.phitrading.pricing.web.dto;

import java.math.BigDecimal;

/**
 * Response payload representing an instrument's pricing information.
 */
public class InstrumentPriceResponse {

    private String symbol;
    private String name;
    private BigDecimal lastPrice;
    private BigDecimal previousClose;

    public InstrumentPriceResponse() {
    }

    public InstrumentPriceResponse(String symbol, String name, BigDecimal lastPrice, BigDecimal previousClose) {
        this.symbol = symbol;
        this.name = name;
        this.lastPrice = lastPrice;
        this.previousClose = previousClose;
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
}
