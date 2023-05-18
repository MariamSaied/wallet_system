package com.ontop.wallets.wallets_domain_service.domain.model.transfer;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transfer {
    private Long userId;
    private Long beneficiaryId;
    private BigDecimal amount;

}
