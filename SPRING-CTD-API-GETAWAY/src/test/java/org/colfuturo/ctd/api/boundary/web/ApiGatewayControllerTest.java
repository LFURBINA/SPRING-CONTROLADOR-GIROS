package org.colfuturo.ctd.api.boundary.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.colfuturo.ctd.api.application.BatchServiceClient;
import org.colfuturo.ctd.api.application.CurrencyServiceClient;
import org.colfuturo.ctd.api.dto.BatchDetails;
import org.colfuturo.ctd.api.dto.OrderDetails;
import org.colfuturo.ctd.api.dto.PaymentDetails;
import org.colfuturo.ctd.api.dto.CtdCurrencies;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ApiGatewayController.class)
@Import({ReactiveResilience4JAutoConfiguration.class, CircuitBreakerConfiguration.class})
class ApiGatewayControllerTest {

    @MockBean
    private BatchServiceClient batchServiceClient;

    @MockBean
    private CurrencyServiceClient currencyServiceClient;

    @Autowired
    private WebTestClient client;


    @Test
    void getOwnerDetails_withAvailableVisitsService() {
        BatchDetails owner = new BatchDetails();
        OrderDetails order = new OrderDetails();
        order.setId(20);
        order.setName("Garfield");
        owner.getOrders().add(order);
        Mockito
            .when(batchServiceClient.getQuote(1))
            .thenReturn(Mono.just(owner));

        CtdCurrencies visits = new CtdCurrencies();
        PaymentDetails visit = new PaymentDetails();
        visit.setId(300);
        visit.setDescription("First payment");
        visit.setPaymentId(order.getId());
        visits.getItems().add(visit);
        Mockito
            .when(currencyServiceClient.getCurrencyForQuotes(Collections.singletonList(order.getId())))
            .thenReturn(Mono.just(visits));

        client.get()
            .uri("/api/gateway/batch/1")
            .exchange()
            .expectStatus().isOk()
            //.expectBody(String.class)
            //.consumeWith(response ->
            //    Assertions.assertThat(response.getResponseBody()).isEqualTo("Garfield"));
            .expectBody()
            .jsonPath("$.pets[0].name").isEqualTo("Garfield")
            .jsonPath("$.pets[0].visits[0].description").isEqualTo("First visit");
    }

    /**
     * Test Resilience4j fallback method
     */
    @Test
    void getOwnerDetails_withServiceError() {
        BatchDetails owner = new BatchDetails();
        OrderDetails cat = new OrderDetails();
        cat.setId(20);
        cat.setName("Garfield");
        owner.getOrders().add(cat);
        Mockito
            .when(batchServiceClient.getQuote(1))
            .thenReturn(Mono.just(owner));

        Mockito
            .when(currencyServiceClient.getCurrencyForQuotes(Collections.singletonList(cat.getId())))
            .thenReturn(Mono.error(new ConnectException("Simulate error")));

        client.get()
            .uri("/api/gateway/owners/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.pets[0].name").isEqualTo("Garfield")
            .jsonPath("$.pets[0].orders").isEmpty();
    }

}
