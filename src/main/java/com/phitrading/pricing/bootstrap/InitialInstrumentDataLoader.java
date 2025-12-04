package com.phitrading.pricing.bootstrap;

import com.phitrading.pricing.domain.entity.InstrumentPrice;
import com.phitrading.pricing.domain.repository.InstrumentPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Seeds initial instrument data on application startup so that
 * dependent applications have instruments to work with.
 */
@Component
public class InitialInstrumentDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(InitialInstrumentDataLoader.class);

    private final InstrumentPriceRepository instrumentPriceRepository;

    public InitialInstrumentDataLoader(InstrumentPriceRepository instrumentPriceRepository) {
        this.instrumentPriceRepository = instrumentPriceRepository;
    }

    @Override
    public void run(String... args) {
        seedInstrument("AAPL", "Apple Inc.", new BigDecimal("189.20"), new BigDecimal("188.00"));
        seedInstrument("TSLA", "Tesla Motors", new BigDecimal("254.70"), new BigDecimal("252.00"));
        seedInstrument("NVDA", "NVIDIA Corp", new BigDecimal("490.55"), new BigDecimal("485.00"));
    }

    private void seedInstrument(String symbol, String name, BigDecimal lastPrice, BigDecimal previousClose) {
        try {
            boolean exists = instrumentPriceRepository.findBySymbol(symbol).isPresent();
            if (exists) {
                return; // idempotent: do nothing if present
            }

            InstrumentPrice ip = new InstrumentPrice();
            ip.setSymbol(symbol);
            ip.setName(name);
            ip.setLastPrice(lastPrice);
            ip.setPreviousClose(previousClose);

            // createdAt/updatedAt are handled by @CreationTimestamp/@UpdateTimestamp in BaseEntity
            instrumentPriceRepository.save(ip);
            log.info("Seeded default instrument {} with lastPrice={} previousClose={}", symbol, lastPrice, previousClose);
        } catch (Exception e) {
            // Do not fail the app on seeding errors
            log.warn("Failed to seed instrument {}: {}", symbol, e.getMessage());
        }
    }
}
