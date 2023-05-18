package com.ontop.wallets.wallets_service.infrastructure.integration.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IntegrationErrorResponseEntity {
    private String code;
    private String message;
}
