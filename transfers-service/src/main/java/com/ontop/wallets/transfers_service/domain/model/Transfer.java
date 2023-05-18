package com.ontop.wallets.transfers_service.domain.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transfer {
    private SourceAccount source;
    private DestinationAccount destination;
    private BigDecimal amount;
}
