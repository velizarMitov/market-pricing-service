# Market Pricing Service

## Overview
The Market Pricing Service is a Spring Boot 3 microservice that simulates and serves market prices for financial instruments (e.g., stocks). It is consumed by the phi-trading-exchange-main application over HTTP (typically via OpenFeign) to retrieve current prices and manage instruments for demo and development scenarios.

## Features
- REST API for instruments and prices:
  - List instruments.
  - Get current price for a symbol.
  - Create, update, and delete instruments (admin/setup use).
- Price simulation engine:
  - A scheduled job periodically updates prices using a small random-walk change around the previous close.
  - Basic market-like movement with advancers/decliners through random variation.
- Caching:
  - GET price endpoint is cached in-memory to reduce load on the database. Cache name: `instrumentPrices`.

## API Endpoints
Base path: `/api/instruments`

- GET `/api/instruments` — list all instruments with their latest pricing snapshot.
- GET `/api/instruments/{symbol}/price` — get current price data for a symbol (cached).
- POST `/api/instruments` — create a new instrument with an initial price.
- PUT `/api/instruments/{symbol}/price` — update the last price for an existing instrument.
- DELETE `/api/instruments/{symbol}` — delete an instrument by its symbol.

Notes:
- An endpoint for instrument details by symbol (GET `/api/instruments/{symbol}`) is not implemented in this service at the moment. Use the list endpoint or price endpoint depending on your need.

### DTOs
- InstrumentPriceResponse (returned by price endpoints):
  - `symbol` (string)
  - `name` (string)
  - `lastPrice` (number)
  - `previousClose` (number)

Example response (GET `/api/instruments/AAPL/price`):

```json
{
  "symbol": "AAPL",
  "name": "Apple Inc.",
  "lastPrice": 189.23,
  "previousClose": 188.70
}
```

### Request examples
Create instrument (POST `/api/instruments`):

```json
{
  "symbol": "AAPL",
  "name": "Apple Inc.",
  "initialPrice": 188.70
}
```

Update price (PUT `/api/instruments/AAPL/price`):

```json
{
  "newPrice": 189.23
}
```

## Architecture
- Spring Boot 3, Java 17
- JPA/Hibernate with MySQL by default (can be switched via configuration)
- Caching via Spring Cache (in-memory ConcurrentMapCache)
- Scheduled tasks:
  - `PriceSimulationScheduler` uses an `@Scheduled` job (cron: `0 */1 * * * *`) to update prices approximately every minute for all instruments. The job also evicts the price cache so subsequent reads refresh cached values.

## Running the service
### Prerequisites
- Java 17+
- Maven
- MySQL running (defaults configured in `src/main/resources/application.properties`)

Default server port: `8081`

### Build & run
```bash
mvn clean install
mvn spring-boot:run
```

The service will be available at:
```
http://localhost:8081/
```

## Integration with Phi-Trading Exchange
The phi-trading-exchange-main application calls this service’s `/api/instruments/...` endpoints (commonly via OpenFeign) to fetch current prices and manage instruments. For full functionality in an integrated environment, ensure both services are running and that the exchange application points to this service’s base URL (e.g., `http://localhost:8081`).

## Testing
This project includes:
- Unit tests for the pricing service layer and repository
- Web tests for `InstrumentPriceController` using MockMvc

Run tests with:
```bash
mvn test
```

## Project structure
- `com.phitrading.pricing.domain` — entities, repositories, services, and the price simulation scheduler
  - `entity` — JPA entities (e.g., InstrumentPrice)
  - `repository` — Spring Data repositories
  - `service` — service interfaces and implementations
  - `service.scheduler` — scheduled tasks (price simulation)
- `com.phitrading.pricing.web` — REST controllers and DTOs
  - `controller` — REST endpoints (InstrumentPriceController, GlobalExceptionHandler)
  - `dto` — request/response DTOs (CreateInstrumentRequest, UpdatePriceRequest, InstrumentPriceResponse, InstrumentPriceDto)
- `com.phitrading.pricing.config` — configuration (CacheConfig)

## Configuration highlights
See `src/main/resources/application.properties` for key settings:
- `server.port=8081`
- MySQL connection (URL/username/password)
- JPA/Hibernate settings
- Simple cache and scheduler pool settings
