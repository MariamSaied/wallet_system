package com.ontop.wallets.wallets_service.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ontop.wallets.wallets_service.common.enums.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class WalletTransaction {
    private Long walletTransactionId;
    private Long userId;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private Operation operation;

    public void setBalances(BigDecimal balanceBefore){
        this.balanceBefore = balanceBefore;

        this.balanceAfter = switch (this.operation){
            case TOPUP -> this.balanceBefore.add(this.amount);
            case WITHDRAW -> this.balanceBefore.subtract(this.amount);
        };
    }
}
