package com.ontop.wallets.wallets_service.infrastructure.integration.rest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ontop.wallets.wallets_service.domain.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetWalletBalance {
    @JsonProperty(value = "user_id")
    private Long userId;
    private BigDecimal balance;

    public Wallet toUser(){
        return new Wallet(this.userId,this.balance);
    }
}
