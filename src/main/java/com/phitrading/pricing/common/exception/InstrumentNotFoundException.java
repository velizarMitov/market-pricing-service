package com.phitrading.pricing.common.exception;

/**
 * Thrown when an instrument identified by its symbol is not found.
 */
public class InstrumentNotFoundException extends RuntimeException {

    public InstrumentNotFoundException(String symbol) {
        super("Instrument with symbol '" + symbol + "' not found");
    }

    public InstrumentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
