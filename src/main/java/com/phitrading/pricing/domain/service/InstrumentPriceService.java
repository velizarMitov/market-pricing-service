package com.phitrading.pricing.domain.service;

import com.phitrading.pricing.web.dto.CreateInstrumentRequest;
import com.phitrading.pricing.web.dto.InstrumentPriceResponse;
import com.phitrading.pricing.web.dto.UpdatePriceRequest;

public interface InstrumentPriceService {

    InstrumentPriceResponse createInstrument(CreateInstrumentRequest request);

    InstrumentPriceResponse updatePrice(String symbol, UpdatePriceRequest request);

    InstrumentPriceResponse getPrice(String symbol);
}
