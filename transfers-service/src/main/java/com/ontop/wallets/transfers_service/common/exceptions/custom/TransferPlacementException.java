package com.ontop.wallets.transfers_service.common.exceptions.custom;

import com.ontop.wallets.transfers_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.transfers_service.domain.model.Transfer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferPlacementException extends RuntimeException{

    private Transfer transfer;

    public TransferPlacementException(Transfer transfer){
        super(ErrorMessages.TRANSFER_PLACEMENT_EXCEPTION + transfer.getDestination().getAccount().getAccountNumber());
        this.transfer = transfer;
    }
}