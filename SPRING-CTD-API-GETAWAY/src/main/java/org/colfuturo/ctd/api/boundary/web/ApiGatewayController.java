/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.colfuturo.ctd.api.boundary.web;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.colfuturo.ctd.api.application.BatchServiceClient;
import org.colfuturo.ctd.api.application.CurrencyServiceClient;
import org.colfuturo.ctd.api.dto.BatchDetails;
import org.colfuturo.ctd.api.dto.CtdCurrencies;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Luis Urbina
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gateway")
public class ApiGatewayController {

    private final BatchServiceClient lotsServiceClient;

    private final CurrencyServiceClient visitsServiceClient;

    private final ReactiveCircuitBreakerFactory cbFactory;

    @GetMapping(value = "batch/{batchId}")
    public Mono<BatchDetails> getQuoteDetails(final @PathVariable int bachtId) {
        return lotsServiceClient.getQuote(bachtId)
            .flatMap(payment ->
                visitsServiceClient.getCurrencyForQuotes(payment.getPaymentIds())
                    .transform(it -> {
                        ReactiveCircuitBreaker cb = cbFactory.create("getQuoteDetails");
                        return cb.run(it, throwable -> emptyQuotesForBatchs());
                    })
                    .map(addConversionsToQuotes(payment))
            );

    }

    private Function<CtdCurrencies, BatchDetails> addConversionsToQuotes(BatchDetails batch) {
        return orders -> {
            batch.getOrders()
                .forEach(payment -> payment.getPayments()
                    .addAll(orders.getItems().stream()
                        .filter(v -> v.getId() == payment.getId())
                        .collect(Collectors.toList()))
                );
            return batch;
        };
    }

    private Mono<CtdCurrencies> emptyQuotesForBatchs() {
        return Mono.just(new CtdCurrencies());
    }
}
