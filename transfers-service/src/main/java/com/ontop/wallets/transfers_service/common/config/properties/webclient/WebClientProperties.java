package com.ontop.wallets.transfers_service.common.config.properties.webclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

