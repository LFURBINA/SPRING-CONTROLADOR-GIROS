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
package org.colfuturo.ctd.api.application;

import lombok.RequiredArgsConstructor;
import org.colfuturo.ctd.api.dto.CtdCurrencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * @author Maciej Szarlinski
 */
@Component
@RequiredArgsConstructor
public class CurrencyServiceClient {

    // Could be changed for testing purpose
    private String hostname = "http://currency-service/";

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<CtdCurrencies> getCurrencyForQuotes(final List<Integer> petIds) {
        return webClientBuilder.build()
            .get()
            .uri(hostname + "showResourcesCtdCurrencyList")
            .retrieve()
            .bodyToMono(CtdCurrencies.class);
    }

    private String joinIds(List<Integer> petIds) {
        return petIds.stream().map(Object::toString).collect(joining(","));
    }

    void setHostname(String hostname) {
        this.hostname = hostname;
    }

	public CurrencyServiceClient(Builder webClientBuilder) {
		super();
		this.webClientBuilder = webClientBuilder;
	}
}
