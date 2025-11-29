package com.phitrading.pricing.domain.service;

import com.phitrading.pricing.web.dto.CreateInstrumentRequest;
import com.phitrading.pricing.web.dto.InstrumentPriceResponse;
import com.phitrading.pricing.web.dto.UpdatePriceRequest;
import com.phitrading.pricing.web.dto.InstrumentPriceDto;

import java.util.List;

public interface InstrumentPriceService {

    InstrumentPriceResponse createInstrument(CreateInstrumentRequest request);

    InstrumentPriceResponse updatePrice(String symbol, UpdatePriceRequest request);

    InstrumentPriceResponse getPrice(String symbol);

    List<InstrumentPriceDto> listAll();

    void deleteBySymbol(String symbol);
}
