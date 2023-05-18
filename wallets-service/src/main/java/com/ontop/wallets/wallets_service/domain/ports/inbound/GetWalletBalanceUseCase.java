package com.ontop.wallets.wallets_service.domain.ports.inbound;


import com.ontop.wallets.wallets_service.domain.model.Wallet;

public interface GetWalletBalanceUseCase {
    Wallet getWalletBalance(Long userId);
}
