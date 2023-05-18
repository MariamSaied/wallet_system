package com.ontop.wallets.wallets_service.domain.ports.outbound;

import com.ontop.wallets.wallets_service.domain.model.Wallet;

public interface GetWalletBalancePort {
    Wallet getWalletBalance(Long userId);
}
