package com.ontop.wallets.wallets_service.common.config.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "web-client")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebClientProperties{
    private Services services;
    private Integer timeout;
}

