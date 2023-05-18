package com.ontop.wallets.transfers_service.common.config;

import com.ontop.wallets.transfers_service.common.config.properties.webclient.WebClientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WebClientProperties.class)
class WebClientConfiguration {

    private final WebClientProperties webClientProperties;

    @Bean
    public WebClient webClient(){
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientProperties.getTimeout())
            .responseTimeout(Duration.ofMillis(webClientProperties.getTimeout()))
            .doOnConnected( conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(webClientProperties.getTimeout(), TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(webClientProperties.getTimeout(), TimeUnit.MILLISECONDS))
            );

        return WebClient.builder()
            .baseUrl(webClientProperties.getServices().getTransferService().getBaseUri())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

}