package com.ontop.wallets.wallets_domain_service.common.config.properties.webclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceProperties{
    private String baseUri;
    private String apiPrefix;
}
