package com.phitrading.pricing.domain.repository;

import com.phitrading.pricing.domain.entity.InstrumentPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InstrumentPriceRepository extends JpaRepository<InstrumentPrice, UUID> {
    Optional<InstrumentPrice> findBySymbol(String symbol);
}
