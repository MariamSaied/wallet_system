package com.ontop.wallets.wallets_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet {
    private Long userId;
    private BigDecimal balance;

    public Boolean hasEnoughBalance(BigDecimal transferAmount){
        return this.balance.compareTo(transferAmount.abs()) > 0;
    }

}
