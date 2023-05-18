package com.ontop.wallets.wallets_service.common.config.properties;

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
