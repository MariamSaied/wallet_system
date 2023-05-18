package com.ontop.wallets.wallets_service.domain.ports.inbound;

import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;

public interface CreateWalletTransactionUseCase {
    WalletTransaction createWalletTransaction(WalletTransaction walletTransaction);
}
