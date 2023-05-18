package com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DestinationAccount {
    private String name;
    private Account account;
}
