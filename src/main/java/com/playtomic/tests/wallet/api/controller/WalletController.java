package com.playtomic.tests.wallet.api.controller;

import com.playtomic.tests.wallet.api.request.TopUpRequest;
import com.playtomic.tests.wallet.api.response.BalanceResponse;
import com.playtomic.tests.wallet.api.response.TopUpResponse;
import com.playtomic.tests.wallet.service.impl.WalletServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * We should think about the service security.
 * There are a lot of practices that depend on the environment and a global security approach.
 */
@RestController
@Slf4j
@Validated
public class WalletController {

    private final WalletServiceImpl walletService;

    @Autowired
    public WalletController(WalletServiceImpl walletService) {
        this.walletService = walletService;
    }

    @GetMapping("balance/{uuid}")
    public BalanceResponse showBalance(@NotEmpty @PathVariable("uuid") String uuid) {
        log.info("balance request for a wallet {}", uuid);
        return new BalanceResponse(uuid, walletService.getWalletBalance(uuid));
    }

    @PostMapping("top-up")
    public TopUpResponse topUpWallet(@Valid @RequestBody TopUpRequest topUpRequest) {
        log.info("Top-up request {}", topUpRequest);
        var paymentUuid = walletService.topUpByCard(topUpRequest);
        return new TopUpResponse(topUpRequest.getWalletUuid(), topUpRequest.getAmount(), paymentUuid);
    }
}
