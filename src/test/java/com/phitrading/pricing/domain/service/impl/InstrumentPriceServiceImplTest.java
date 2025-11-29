package com.phitrading.pricing.domain.service.impl;

import com.phitrading.pricing.domain.entity.InstrumentPrice;
import com.phitrading.pricing.domain.repository.InstrumentPriceRepository;
import com.phitrading.pricing.web.dto.InstrumentPriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstrumentPriceServiceImplTest {

    @Mock
    private InstrumentPriceRepository repository;

    @InjectMocks
    private InstrumentPriceServiceImpl service;

    @Test
    void getPrice_returnsCurrentPriceFromRepository() {
        // Arrange
        InstrumentPrice entity = new InstrumentPrice();
        entity.setSymbol("AAPL");
        entity.setName("Apple Inc.");
        entity.setLastPrice(new BigDecimal("150.50"));
        entity.setPreviousClose(new BigDecimal("149.00"));

        when(repository.findBySymbolIgnoreCase("AAPL")).thenReturn(Optional.of(entity));

        // Act
        InstrumentPriceResponse response = service.getPrice("AAPL");

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getSymbol()).isEqualTo("AAPL");
        assertThat(response.getLastPrice()).isEqualByComparingTo("150.50");
    }
}
