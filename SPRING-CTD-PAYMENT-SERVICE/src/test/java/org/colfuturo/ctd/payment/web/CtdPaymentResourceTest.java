package org.colfuturo.ctd.payment.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.colfuturo.ctd.payment.model.CtdPayment;
import org.colfuturo.ctd.payment.model.CtdPaymentRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CtdPaymentResource.class)
@ActiveProfiles("test")
class CtdPaymentResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CtdPaymentRepository paymentRepository;

    @Test
    void shouldFetchVisits() throws Exception {
    	
        given(paymentRepository.findByPymtPaymentIdIn(asList(111, 222)))
            .willReturn(
                asList(               )
            );

        mvc.perform(get("/payment?pymtPaymentId=111,222"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items[0].id").value(1))
            .andExpect(jsonPath("$.items[1].id").value(2))
            .andExpect(jsonPath("$.items[2].id").value(3))
            .andExpect(jsonPath("$.items[0].pymtPaymentId").value(111))
            .andExpect(jsonPath("$.items[1].pymtPaymentId").value(222))
            .andExpect(jsonPath("$.items[2].pymtPaymentId").value(222));
    }
}
