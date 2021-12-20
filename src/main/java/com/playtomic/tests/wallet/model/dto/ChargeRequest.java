package com.playtomic.tests.wallet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeRequest {

    @NonNull
    @JsonProperty("credit_card")
    String creditCardNumber;

    @NonNull
    @JsonProperty("amount")
    BigDecimal amount;
}
