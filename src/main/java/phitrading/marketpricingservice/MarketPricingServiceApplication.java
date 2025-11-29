package phitrading.marketpricingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"phitrading.marketpricingservice", "com.phitrading.pricing"})
@EntityScan(basePackages = {"com.phitrading.pricing"})
@EnableJpaRepositories(basePackages = {"com.phitrading.pricing"})
@ComponentScan(basePackages = {"phitrading.marketpricingservice", "com.phitrading.pricing"})
@EnableScheduling
@EnableCaching
public class MarketPricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketPricingServiceApplication.class, args);
    }

}
