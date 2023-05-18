package com.ontop.wallets.wallets_service.infrastructure.integration.rest.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ontop.wallets.wallets_service.common.enums.Operation;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateWalletTransaction {
    @JsonProperty("wallet_transaction_id")
    private Long walletTransactionId;
    @JsonProperty("user_id")
    private Long userId;
    private BigDecimal amount;

    public WalletTransaction toWalletTransaction(){
        boolean isWithdrawalOperation = this.amount.compareTo(BigDecimal.ZERO) > 0;
        if(isWithdrawalOperation)
            return new WalletTransaction(this.walletTransactionId,this.userId,this.amount,BigDecimal.ZERO,BigDecimal.ZERO,Operation.TOPUP);
        else
            return new WalletTransaction(this.walletTransactionId,this.userId,this.amount.negate(),BigDecimal.ZERO,BigDecimal.ZERO,Operation.WITHDRAW);
    }

    static public CreateWalletTransaction fromWalletTransaction(WalletTransaction walletTransaction){
        return new CreateWalletTransaction(walletTransaction.getWalletTransactionId(), walletTransaction.getUserId(),
                walletTransaction.getAmount());
    }
}