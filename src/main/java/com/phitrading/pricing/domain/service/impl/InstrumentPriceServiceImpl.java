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
    public InstrumentPriceResponse createInstrument(CreateInstrumentRequest request) {
        InstrumentPrice entity = new InstrumentPrice();
        entity.setSymbol(request.getSymbol());
        entity.setName(request.getName());
        entity.setLastPrice(request.getInitialPrice());
        entity.setPreviousClose(request.getInitialPrice());
        InstrumentPrice saved = repository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public InstrumentPriceResponse updatePrice(String symbol, UpdatePriceRequest request) {
        InstrumentPrice entity = repository.findBySymbol(symbol)
                .orElseThrow(() -> new InstrumentNotFoundException(symbol));
        entity.setLastPrice(request.getNewPrice());
        InstrumentPrice saved = repository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InstrumentPriceResponse getPrice(String symbol) {
        InstrumentPrice entity = repository.findBySymbol(symbol)
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

    private InstrumentPriceResponse toResponse(InstrumentPrice entity) {
        return new InstrumentPriceResponse(
                entity.getSymbol(),
                entity.getName(),
                entity.getLastPrice(),
                entity.getPreviousClose()
        );
    }
}
