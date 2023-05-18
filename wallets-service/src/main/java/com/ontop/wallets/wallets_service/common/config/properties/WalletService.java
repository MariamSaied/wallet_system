package com.ontop.wallets.wallets_service.common.config.properties;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletService extends ServiceProperties{
    private String getBalanceApi;
    private String createTransactionApi;
    private String apiPrefix;
}
