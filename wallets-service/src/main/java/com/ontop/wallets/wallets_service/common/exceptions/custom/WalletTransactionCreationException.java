package com.ontop.wallets.wallets_service.common.exceptions.custom;

import com.ontop.wallets.wallets_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletTransactionCreationException extends RuntimeException{
    private WalletTransaction walletTransaction;
    public WalletTransactionCreationException(WalletTransaction walletTransaction){
        super(ErrorMessages.WALLET_TRANSACTION_FAILURE + walletTransaction.getAmount());

        /* Amount is in negative to comply with Wallets Gateway Interface.
                Using abs() to preserve the Service Interface and depend on Operation not amount sign
            */
        walletTransaction.setAmount(walletTransaction.getAmount().abs());

        this.walletTransaction = walletTransaction;
    }
}
