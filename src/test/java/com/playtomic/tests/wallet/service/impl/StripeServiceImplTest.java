package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.model.dto.ChargeRequest;
import com.playtomic.tests.wallet.model.dto.ChargeResponse;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.exception.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.exception.StripeServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {StripeServiceImpl.class, ValidationAutoConfiguration.class})
class StripeServiceImplTest {

    private static final ChargeResponse EXPECTED_RESPONSE = new ChargeResponse("213", new BigDecimal(15));
    private static final String CREDIT_CARD_NUMBER = "4242 4242 4242 4242";

    @Autowired
    private StripeService service;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${stripe.simulator.charges-uri}")
    private URI chargesUri;

    @Test
    void test_exception() {
        var amount = new BigDecimal(5);
        when(restTemplate.postForEntity(eq(chargesUri), eq(new ChargeRequest(CREDIT_CARD_NUMBER, amount)), any())).thenThrow(
                StripeAmountTooSmallException.class);
        assertThrows(StripeAmountTooSmallException.class, () -> {
            service.charge(CREDIT_CARD_NUMBER, amount);
        });
    }

    @Test
    void test_ok() throws StripeServiceException {
        var amount = new BigDecimal(15);
        when(restTemplate.postForEntity(eq(chargesUri), any(), eq(ChargeResponse.class)))
                .thenReturn(new ResponseEntity<>(EXPECTED_RESPONSE, HttpStatus.OK));
        var response = service.charge(CREDIT_CARD_NUMBER, amount);
        assertEquals(EXPECTED_RESPONSE, response);
    }
}
