package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUuid(String uuid);

}
