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
package org.colfuturo.ctd.currency.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.colfuturo.ctd.currency.model.CtdCurrency;
import org.colfuturo.ctd.currency.model.CtdCurrencyRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Luis Urbina
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(CtdCurrencyResource.class)
@ActiveProfiles("test")
class CtdCurrencyResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CtdCurrencyRepository ctdCurrencyRepository;

    @Test
    void shouldGetAListOfVets() throws Exception {

    	CtdCurrency ctdCurrency = new CtdCurrency();
        ctdCurrency.setCcyId(1);

        given(ctdCurrencyRepository.findAll()).willReturn(asList(ctdCurrency));

        mvc.perform(get("/currency").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }
}
