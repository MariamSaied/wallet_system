package com.ontop.wallets.wallets_domain_service.application.rest.responses;

import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.responses.TransferServiceResponse;
import com.ontop.wallets.wallets_domain_service.domain.model.wallet.WalletTransaction;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferResponse {
    private Long userId;
    private Long beneficiaryId;
    private BigDecimal amount;
    private WalletTransaction walletTransaction;
    private TransferServiceResponse transfer;
    
}
