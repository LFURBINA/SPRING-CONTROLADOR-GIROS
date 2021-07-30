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
package org.colfuturo.ctd.payment.web;

import java.util.List;

import javax.validation.Valid;

import org.colfuturo.ctd.payment.model.CtdPayment;
import org.colfuturo.ctd.payment.model.CtdPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Luis Urbina
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Timed("ctd.payment")
class CtdPaymentResource {
	@Autowired
    private CtdPaymentRepository paymentRepository;	
    @PostMapping("payment")
    @ResponseStatus(HttpStatus.CREATED)
   public CtdPayment create(
        @Valid @RequestBody CtdPayment payment) {

        log.info("Saving payment {}", payment);
        return paymentRepository.save(payment);
    }

    @GetMapping("payment/{pymtId}")
   public List<CtdPayment> payments(@PathVariable("pymtId") int pymtId) {
        return paymentRepository.findByPymtId(pymtId);
    }

    @GetMapping("payment")
   public CtdPayments paymentsMultiGet(@RequestParam("pymtPaymentId") List<Integer> pymtPaymentIds) {
        final List<CtdPayment> byClientReferenceIdIn = paymentRepository.findByPymtPaymentIdIn(pymtPaymentIds);
        return new CtdPayments(byClientReferenceIdIn);
    }

    @Value
    @Builder
    static class CtdPayments {
        List<CtdPayment> items;
        CtdPayments(List<CtdPayment> items){
        	this.items = items;
        }
    }
}
