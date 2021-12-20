package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.api.request.TopUpRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface WalletService {

    BigDecimal getWalletBalance(@NotEmpty String uuid);

    String topUpByCard(@NotNull TopUpRequest topUpRequest);
}
