package com.ontop.wallets.transfers_service.domain.ports.inbound;

import com.ontop.wallets.transfers_service.application.rest.request.TransferRequest;
import com.ontop.wallets.transfers_service.infrastructure.integration.rest.responses.TransferResponse;

public interface DoTransferUseCase {
    TransferResponse doTransfer(TransferRequest transferRequest);
}
