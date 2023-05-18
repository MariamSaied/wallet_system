package com.ontop.wallets.transfers_service.infrastructure.integration.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferResponse {
    private RequestInfo requestInfo;
    private PaymentInfo paymentInfo;
}
