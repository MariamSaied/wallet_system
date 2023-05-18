package com.ontop.wallets.wallets_domain_service.domain.ports.outbound;

import com.ontop.wallets.wallets_domain_service.domain.model.wallet.WalletTransaction;

public interface WalletsPort {
    WalletTransaction createWalletTransaction(WalletTransaction walletTransaction);
}
