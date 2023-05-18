package com.ontop.wallets.wallets_domain_service.domain.ports.inbound;

import com.ontop.wallets.wallets_domain_service.application.rest.responses.TransferResponse;
import com.ontop.wallets.wallets_domain_service.domain.model.transfer.Transfer;

public interface DoTransferUseCase {
    TransferResponse doTransfer(Transfer transferRequest);
}
