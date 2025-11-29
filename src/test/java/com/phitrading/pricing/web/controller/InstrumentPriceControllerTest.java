package com.phitrading.pricing.web.controller;

import com.phitrading.pricing.domain.service.InstrumentPriceService;
import com.phitrading.pricing.web.dto.InstrumentPriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class InstrumentPriceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InstrumentPriceService service;

    @BeforeEach
    void setUp() {
        // Build standalone MockMvc with manually constructed controller
        InstrumentPriceController controller = new InstrumentPriceController(service);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getCurrentPrice_returnsOkAndBody() throws Exception {
        // Arrange
        InstrumentPriceResponse response = new InstrumentPriceResponse(
                "AAPL",
                "Apple Inc.",
                new BigDecimal("123.45"),
                new BigDecimal("120.00")
        );
        when(service.getPrice("AAPL")).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/instruments/{symbol}/price", "AAPL")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.lastPrice").value(123.45));
    }
}
