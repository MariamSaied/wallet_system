package com.ontop.wallets.wallets_domain_service.domain.ports.outbound;

import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests.TransferRequest;
import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.responses.TransferServiceResponse;

public interface TransfersPort {
    TransferServiceResponse doTransfer(TransferRequest transferRequest);
}
