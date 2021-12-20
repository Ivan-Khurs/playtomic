package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.model.dto.ChargeRequest;
import com.playtomic.tests.wallet.model.dto.ChargeResponse;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.exception.StripeServiceException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URI;

/**
 * Handles the communication with Stripe.
 * <p>
 * A real implementation would call to String using their API/SDK.
 * This dummy implementation throws an error when trying to charge less than 10€.
 */
@Service
@Validated
public class StripeServiceImpl implements StripeService {

    @NonNull
    private URI chargesUri;

    @NonNull
    private URI refundsUri;

    @NonNull
    private RestTemplate restTemplate;

    public StripeServiceImpl(@Value("${stripe.simulator.charges-uri}") @NotNull URI chargesUri,
                             @Value("${stripe.simulator.refunds-uri}") @NotNull URI refundsUri,
                             @NotNull RestTemplate restTemplate) {
        this.chargesUri = chargesUri;
        this.refundsUri = refundsUri;
        this.restTemplate = restTemplate;
    }

    /**
     * Charges money in the credit card.
     * <p>
     * Ignore the fact that no CVC or expiration date are provided.
     *
     * @param creditCardNumber The number of the credit card
     * @param amount           The amount that will be charged.
     *
     * @throws StripeServiceException
     */
    public ChargeResponse charge(@NotNull String creditCardNumber, @NotNull BigDecimal amount) {
        ChargeRequest body = new ChargeRequest(creditCardNumber, amount);
        return restTemplate.postForEntity(chargesUri, body, ChargeResponse.class).getBody();
    }

    /**
     * Refunds the specified payment.
     */
    public void refund(@NotNull String paymentId) throws StripeServiceException {
        // Object.class because we don't read the body here.
        restTemplate.postForEntity(refundsUri.toString(), null, Object.class, paymentId);
    }
}
