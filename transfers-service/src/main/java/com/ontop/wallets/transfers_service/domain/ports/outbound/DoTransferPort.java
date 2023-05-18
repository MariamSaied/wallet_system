package com.ontop.wallets.transfers_service.domain.ports.outbound;

import com.ontop.wallets.transfers_service.infrastructure.integration.rest.responses.TransferResponse;
import com.ontop.wallets.transfers_service.domain.model.Transfer;

import java.util.Optional;

public interface DoTransferPort {
    TransferResponse doTransfer(Transfer transfer);
}
