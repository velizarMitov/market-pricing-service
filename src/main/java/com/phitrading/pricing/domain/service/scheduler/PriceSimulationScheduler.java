package com.phitrading.pricing.domain.service.scheduler;

import com.phitrading.pricing.domain.entity.InstrumentPrice;
import com.phitrading.pricing.domain.repository.InstrumentPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceSimulationScheduler {

    private final InstrumentPriceRepository repository;

    @Scheduled(cron = "0 */1 * * * *")
    @CacheEvict(cacheNames = "instrumentPrices", allEntries = true)
    public void simulatePrices() {
        List<InstrumentPrice> instruments = repository.findAll();
        if (instruments.isEmpty()) {
            log.info("Price simulation: no instruments found");
            return;
        }

        int updated = 0;
        for (InstrumentPrice instrument : instruments) {
            BigDecimal last = instrument.getLastPrice();
            if (last == null) {
                continue;
            }

            // random change between -0.02 and +0.02
            double delta = ThreadLocalRandom.current().nextDouble(-0.02d, 0.0200000001d);
            BigDecimal changePct = BigDecimal.valueOf(delta);

            // Set previous close to last before change (simpler approach)
            instrument.setPreviousClose(last);

            BigDecimal multiplier = BigDecimal.ONE.add(changePct);
            BigDecimal newPrice = last.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
            instrument.setLastPrice(newPrice);
            updated++;
        }

        repository.saveAll(instruments);
        log.info("Price simulation: updated {} instruments", updated);
    }
}
