package com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    private String accountNumber;
    private Currency currency;
    private String routingNumber;
}
