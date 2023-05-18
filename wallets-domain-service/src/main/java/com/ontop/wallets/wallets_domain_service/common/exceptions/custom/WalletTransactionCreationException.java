package com.ontop.wallets.wallets_domain_service.common.exceptions.custom;

import com.ontop.wallets.wallets_domain_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.wallets_domain_service.domain.model.wallet.WalletTransaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletTransactionCreationException extends RuntimeException{

    private WalletTransaction walletTransaction;

    public WalletTransactionCreationException(WalletTransaction walletTransaction){
        super(ErrorMessages.TRANSACTION_CREATION_EXCEPTION);
        this.walletTransaction = walletTransaction;
    }
}
