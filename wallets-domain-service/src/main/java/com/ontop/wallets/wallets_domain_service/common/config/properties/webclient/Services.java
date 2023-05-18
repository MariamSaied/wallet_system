package com.ontop.wallets.wallets_domain_service.common.config.properties.webclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Services{
    private TransferService transferService;
    private WalletService walletService;
    private BeneficiaryService beneficiaryService;
}