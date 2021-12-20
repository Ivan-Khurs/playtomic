package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.api.request.TopUpRequest;
import com.playtomic.tests.wallet.model.entity.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.service.exception.WalletNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * It would be nice to call the 'topUpByCard' method from another TransactionService.
 * Where we can track a transaction status and add it to history.
 */
@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final StripeServiceImpl stripeService;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, StripeServiceImpl stripeService) {
        this.walletRepository = walletRepository;
        this.stripeService = stripeService;
    }

    public BigDecimal getWalletBalance(@NotEmpty String uuid) {
        return findWallet(uuid).getBalance();
    }

    @Transactional
    public String topUpByCard(@NotNull TopUpRequest topUpRequest) {
        var wallet = findWallet(topUpRequest.getWalletUuid());
        var stripeResponse = stripeService.charge(topUpRequest.getCardNumber(), topUpRequest.getAmount());
        wallet.setBalance(wallet.getBalance().add(stripeResponse.getAmount()));
        return stripeResponse.getId();
    }

    private Wallet findWallet(String uuid) {
        return walletRepository.findByUuid(uuid)
                               .orElseThrow(() -> new WalletNotFoundException("Cannot find a wallet with the uuid " + uuid));
    }
}
