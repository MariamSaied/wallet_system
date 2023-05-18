package com.ontop.wallets.transfers_service.common.config.properties.webclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferService extends ServiceProperties {
    private String doTransferApi;
    private String apiPrefix;
}
