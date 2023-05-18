package com.ontop.wallets.wallets_domain_service.common.exceptions.custom;

import com.ontop.wallets.wallets_domain_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests.TransferRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferPlacementException extends RuntimeException{

    private TransferRequest transferRequest;

    public TransferPlacementException(TransferRequest transferRequest){
        super(ErrorMessages.TRANSFER_PLACEMENT_EXCEPTION + transferRequest.getBeneficiary().getAccount().getAccountNumber());
        this.transferRequest = transferRequest;
    }
}