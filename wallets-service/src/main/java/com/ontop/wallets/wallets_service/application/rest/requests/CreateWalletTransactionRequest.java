package com.ontop.wallets.wallets_service.application.rest.requests;

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
public class CreateWalletTransactionRequest {
    private BigDecimal amount;
    private Long userId;
    private Operation operation;

    public WalletTransaction toWalletTransaction(){
        BigDecimal amount = this.operation == Operation.WITHDRAW ?
                this.amount.negate() : this.amount;

        return new WalletTransaction(null,this.userId,amount,null,null,operation);
    }

}
