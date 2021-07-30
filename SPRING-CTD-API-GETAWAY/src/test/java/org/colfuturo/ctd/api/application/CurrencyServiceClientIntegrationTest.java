package org.colfuturo.ctd.api.application;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.colfuturo.ctd.api.dto.CtdCurrencies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurrencyServiceClientIntegrationTest {

    private static final Integer CURRENCY_ID = 1;

    private CurrencyServiceClient currencyServiceClient;

    private MockWebServer server;

    @BeforeEach
    void setUp() {
        server = new MockWebServer();
        currencyServiceClient = new CurrencyServiceClient(WebClient.builder());
        currencyServiceClient.setHostname(server.url("/").toString());
    }

    @AfterEach
    void shutdown() throws IOException {
        this.server.shutdown();
    }

    @Test
    void getCurrencyForQuotes_withAvailableRecipientService() {
        prepareResponse(response -> response
            .setHeader("Content-Type", "application/json")
            .setBody("{\"items\":[{\"id\":5,\"date\":\"2018-11-15\",\"description\":\"test currency\",\"quoteId\":1}]}"));

        Mono<CtdCurrencies> visits = currencyServiceClient.getCurrencyForQuotes(Collections.singletonList(1));

        assertVisitDescriptionEquals(visits.block(), CURRENCY_ID,"test visit");
    }


    private void assertVisitDescriptionEquals(CtdCurrencies currency, int quoteId, String description) {
        assertEquals(1, currency.getItems().size());
        assertNotNull(currency.getItems().get(0));
        assertEquals(quoteId, currency.getItems().get(0).getId());
        assertEquals(description, currency.getItems().get(0).getDescription());
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        this.server.enqueue(response);
    }

}
