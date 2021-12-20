package com.playtomic.tests.wallet.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TopUpResponse {

    private String walletUuid;
    private BigDecimal amount;
    private String paymentUuid;

}
