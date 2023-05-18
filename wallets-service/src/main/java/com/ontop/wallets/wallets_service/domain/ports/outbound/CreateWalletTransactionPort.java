package com.ontop.wallets.wallets_service.domain.ports.outbound;

import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;

public interface CreateWalletTransactionPort {
    WalletTransaction createWalletTransaction(WalletTransaction walletTransaction);
}
