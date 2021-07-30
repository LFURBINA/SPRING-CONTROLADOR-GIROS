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
import org.colfuturo.ctd.api.dto.BatchDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Luis Urbina
 */
@Component
@RequiredArgsConstructor
public class BatchServiceClient {

	@Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<BatchDetails> getQuote(final int lotId) {
        return webClientBuilder.build().get()
            .uri("http://quote-service/quote/{ownerId}", lotId)
            .retrieve()
            .bodyToMono(BatchDetails.class);
    }
}
