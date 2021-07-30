package org.colfuturo.ctd.quote.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.colfuturo.ctd.quote.model.CtdQuote;
import org.colfuturo.ctd.quote.model.CtdQuoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Maciej Szarlinski
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(CtdQuoteResource.class)
@ActiveProfiles("test")
class CtdQuoteResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CtdQuoteRepository petRepository;

    @Test
    void shouldGetACtdQuoteInJSonFormat() throws Exception {

        CtdQuote quote = setupCtdQuote();

        given(petRepository.findById(1)).willReturn(Optional.of(quote));


        mvc.perform(get("/quotes/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.qteQuoteId").value("1"));
    }

    private CtdQuote setupCtdQuote() {
        CtdQuote quote = new CtdQuote();
        quote.setQteId(1);
        quote.setQteQuoteId("1");
        return quote;
    }
}
