package com.phitrading.pricing.domain.service.impl;

import com.phitrading.pricing.common.exception.InstrumentNotFoundException;
import com.phitrading.pricing.domain.entity.InstrumentPrice;
import com.phitrading.pricing.domain.repository.InstrumentPriceRepository;
import com.phitrading.pricing.domain.service.InstrumentPriceService;
import com.phitrading.pricing.web.dto.CreateInstrumentRequest;
import com.phitrading.pricing.web.dto.InstrumentPriceResponse;
import com.phitrading.pricing.web.dto.UpdatePriceRequest;
import com.phitrading.pricing.web.dto.InstrumentPriceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstrumentPriceServiceImpl implements InstrumentPriceService {

    private final InstrumentPriceRepository repository;
    private static final Logger log = LoggerFactory.getLogger(InstrumentPriceServiceImpl.class);

    public InstrumentPriceServiceImpl(InstrumentPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "instrumentPrices", key = "#result.symbol.toUpperCase()")
    public InstrumentPriceResponse createInstrument(CreateInstrumentRequest request) {
        InstrumentPrice entity = new InstrumentPrice();
        entity.setSymbol(request.getSymbol());
        entity.setName(request.getName());
        entity.setLastPrice(request.getInitialPrice());
        entity.setPreviousClose(request.getInitialPrice());
        InstrumentPrice saved = repository.save(entity);
        InstrumentPriceResponse response = toResponse(saved);
        log.info("Cache updated for symbol={} after create", response.getSymbol());
        return response;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "instrumentPrices", key = "#symbol.toUpperCase()")
    public InstrumentPriceResponse updatePrice(String symbol, UpdatePriceRequest request) {
        InstrumentPrice entity = repository.findBySymbolIgnoreCase(symbol)
                .orElseThrow(() -> new InstrumentNotFoundException(symbol));
        entity.setLastPrice(request.getNewPrice());
        InstrumentPrice saved = repository.save(entity);
        InstrumentPriceResponse response = toResponse(saved);
        log.info("Cache updated for symbol={} after price update", symbol);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "instrumentPrices", key = "#symbol.toUpperCase()")
    public InstrumentPriceResponse getPrice(String symbol) {
        log.info("Loading price for symbol={} from DB (cache miss)", symbol);
        InstrumentPrice entity = repository.findBySymbolIgnoreCase(symbol)
                .orElseThrow(() -> new InstrumentNotFoundException(symbol));
        return toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<InstrumentPriceDto> listAll() {
        java.util.List<InstrumentPrice> all = repository.findAll();
        java.util.List<InstrumentPriceDto> result = new java.util.ArrayList<>(all.size());
        for (InstrumentPrice e : all) {
            result.add(new InstrumentPriceDto(
                    e.getId(),
                    e.getSymbol(),
                    e.getName(),
                    e.getLastPrice(),
                    e.getPreviousClose(),
                    e.getUpdatedAt()
            ));
        }
        // INFO log as required
        log.info("Listing {} instruments", result.size());
        return result;
    }

    @Override
    @Transactional
    public void deleteBySymbol(String symbol) {
        InstrumentPrice entity = repository.findBySymbolIgnoreCase(symbol)
                .orElseThrow(() -> new InstrumentNotFoundException(symbol));
        // Log INFO when a symbol is deleted: symbol and id
        log.info("Deleting instrument symbol={} id={}", entity.getSymbol(), entity.getId());
        repository.deleteBySymbol(entity.getSymbol());
    }

    private InstrumentPriceResponse toResponse(InstrumentPrice entity) {
        return new InstrumentPriceResponse(
                entity.getSymbol(),
                entity.getName(),
                entity.getLastPrice(),
                entity.getPreviousClose()
        );
    }
}
