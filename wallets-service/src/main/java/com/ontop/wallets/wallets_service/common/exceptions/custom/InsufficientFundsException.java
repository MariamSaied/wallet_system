package com.ontop.wallets.wallets_service.common.exceptions.custom;

import com.ontop.wallets.wallets_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientFundsException extends RuntimeException{
    private WalletTransaction walletTransaction;
    public InsufficientFundsException(WalletTransaction walletTransaction){
        super(ErrorMessages.INSUFFICIENT_FUNDS);
        this.walletTransaction = walletTransaction;
        // Wallet Transaction passed is negative when it's withdrawal. While we depend on Operation
        this.walletTransaction.setAmount(walletTransaction.getAmount().abs());
    }
}
