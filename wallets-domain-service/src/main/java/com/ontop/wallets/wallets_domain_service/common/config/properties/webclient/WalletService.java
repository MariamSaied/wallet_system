package com.ontop.wallets.wallets_domain_service.common.config.properties.webclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletService extends ServiceProperties {
    private String createWalletTransactionApi;
}
