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
package org.colfuturo.ctd.quote.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.colfuturo.ctd.quote.model.CtdQuoteRepository;
import org.colfuturo.ctd.quote.model.CtdQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Luis Urbina
 */
@RequestMapping("/quote")
@RestController
@Timed("ctd.quote")
@RequiredArgsConstructor
class CtdQuoteResource {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private CtdQuoteRepository ctdQuoteRepository;

    /**
     * Create CtdQuote
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CtdQuote createOwner(@Valid @RequestBody CtdQuote quote) {
        return ctdQuoteRepository.save(quote);
    }

    /**
     * Read single CtdQuote
     */
    @GetMapping(value = "/{quoteId}")
    public Optional<CtdQuote> findOwner(@PathVariable("quoteId") int quoteId) {
        return ctdQuoteRepository.findById(quoteId);
    }

    /**
     * Read List of Owners
     */
    @GetMapping
    public List<CtdQuote> findAll() {
        return ctdQuoteRepository.findAll();
    }

    /**
     * Update CtdQuote
     */
    @PutMapping(value = "/{quoteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOwner(@PathVariable("quoteId") int quoteId, @Valid @RequestBody CtdQuote quoteRequest) {
        final Optional<CtdQuote> quote = ctdQuoteRepository.findById(quoteId);

        final CtdQuote quoteModel = quote.orElseThrow(() -> new ResourceNotFoundException("CtdQuote "+quoteId+" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        quoteModel.setQteAmount(quoteRequest.getQteAmount());
        quoteModel.setQteBuyCurrency(quoteRequest.getQteBuyCurrency());
        quoteModel.setQteCustomerId(quoteRequest.getQteCustomerId());
        quoteModel.setQteExpiry(quoteRequest.getQteExpiry());
        quoteModel.setQteQuoteId(quoteRequest.getQteQuoteId());
        log.info("Saving quote {}", quoteModel);
        ctdQuoteRepository.save(quoteModel);
    }
}
