package com.playtomic.tests.wallet.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpRequest {

    @NotEmpty
    private String walletUuid;
    @NotEmpty
    private String cardNumber;
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal amount;
}
