package com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferServiceResponse {
    private RequestInfo requestInfo;
    private PaymentInfo paymentInfo;
}
