package com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentInfo {
    private BigDecimal amount;
    private UUID id;
}
