package com.ontop.wallets.transfers_service.application.rest.request;

import com.ontop.wallets.transfers_service.domain.model.DestinationAccount;
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
}
