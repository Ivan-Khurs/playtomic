package com.playtomic.tests.wallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeResponse {

    private String id;
    private BigDecimal amount;

}
