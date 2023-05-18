package com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests;

import com.ontop.wallets.wallets_domain_service.domain.model.beneficiary.Beneficiary;
import com.ontop.wallets.wallets_domain_service.domain.model.transfer.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferRequest {
    private DestinationAccount beneficiary;
    private BigDecimal amount;

    public static TransferRequest fromTransferAndBeneficiary(Transfer transfer, Beneficiary beneficiary){
        return new TransferRequest(beneficiary.toDestinationAccount(), transfer.getAmount());
    }
}
