package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.dto.ChargeResponse;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface StripeService {

    ChargeResponse charge(@NotNull String creditCardNumber, @NotNull BigDecimal amount);
}
