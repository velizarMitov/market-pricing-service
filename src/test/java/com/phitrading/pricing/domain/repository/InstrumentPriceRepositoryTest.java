package com.phitrading.pricing.domain.repository;

import com.phitrading.pricing.domain.entity.InstrumentPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {phitrading.marketpricingservice.MarketPricingServiceApplication.class, InstrumentPriceRepositoryTest.Config.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.generate-ddl=true",
        "spring.jpa.properties.hibernate.hbm2ddl.auto=create-drop",
        "spring.jpa.defer-datasource-initialization=true",
        "spring.jpa.show-sql=false"
})
class InstrumentPriceRepositoryTest {

    @Configuration(proxyBeanMethods = false)
    @EnableJpaRepositories(basePackages = "com.phitrading.pricing.domain.repository")
    @EntityScan(basePackages = "com.phitrading.pricing.domain.entity")
    static class Config { }

    @Autowired
    private InstrumentPriceRepository repository;

    @Test
    void findBySymbolIgnoreCase_persistsAndFindsInstrument() {
        // Arrange - persist an instrument
        InstrumentPrice entity = new InstrumentPrice();
        entity.setSymbol("AAPL");
        entity.setName("Apple Inc.");
        entity.setLastPrice(new BigDecimal("150.50"));
        entity.setPreviousClose(new BigDecimal("149.00"));
        repository.save(entity);

        // Act
        var optional = repository.findBySymbolIgnoreCase("aapl");

        // Assert
        assertThat(optional).isPresent();
        var found = optional.get();
        assertThat(found.getSymbol()).isEqualTo("AAPL");
        assertThat(found.getName()).isEqualTo("Apple Inc.");
        assertThat(found.getLastPrice()).isEqualByComparingTo("150.50");
    }
}
