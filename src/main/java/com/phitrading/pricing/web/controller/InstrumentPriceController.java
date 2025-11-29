package com.phitrading.pricing.web.controller;

import com.phitrading.pricing.domain.service.InstrumentPriceService;
import com.phitrading.pricing.web.dto.CreateInstrumentRequest;
import com.phitrading.pricing.web.dto.InstrumentPriceResponse;
import com.phitrading.pricing.web.dto.InstrumentPriceDto;
import com.phitrading.pricing.web.dto.UpdatePriceRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentPriceController {

    private final InstrumentPriceService service;

    public InstrumentPriceController(InstrumentPriceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstrumentPriceResponse createInstrument(@Valid @RequestBody CreateInstrumentRequest request) {
        return service.createInstrument(request);
    }

    @PutMapping("/{symbol}/price")
    public InstrumentPriceResponse updatePrice(@PathVariable String symbol,
                                               @Valid @RequestBody UpdatePriceRequest request) {
        return service.updatePrice(symbol, request);
    }

    @GetMapping("/{symbol}/price")
    public InstrumentPriceResponse getPrice(@PathVariable String symbol) {
        return service.getPrice(symbol);
    }

    @GetMapping
    public java.util.List<InstrumentPriceDto> listInstruments() {
        return service.listAll();
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<Void> delete(@PathVariable String symbol) {
        service.deleteBySymbol(symbol);
        return ResponseEntity.noContent().build();
    }
}
