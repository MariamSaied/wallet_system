package com.ontop.wallets.wallets_service.infrastructure.integration.rest;

import com.ontop.wallets.wallets_service.common.config.properties.WebClientProperties;
import com.ontop.wallets.wallets_service.common.exceptions.custom.WalletBalanceInquiryException;
import com.ontop.wallets.wallets_service.domain.model.Wallet;
import com.ontop.wallets.wallets_service.domain.ports.outbound.GetWalletBalancePort;
import com.ontop.wallets.wallets_service.infrastructure.integration.rest.requests.GetWalletBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@EnableConfigurationProperties(WebClientProperties.class)
public class GetWalletBalanceAdapter implements GetWalletBalancePort {

    private final WebClient webClient;
    private final WebClientProperties webClientProperties;
    private final String walletServiceApiPrefix;

    @Autowired
    public GetWalletBalanceAdapter(WebClient webClient, WebClientProperties webClientProperties){
        this.webClient = webClient;
        this.webClientProperties = webClientProperties;
        this.walletServiceApiPrefix = webClientProperties.getServices().getWalletService().getApiPrefix();
    }

    @Override
    public Wallet getWalletBalance(Long userId) {

        String balanceApiUri = walletServiceApiPrefix +
                webClientProperties.getServices().getWalletService().getGetBalanceApi();

        ResponseEntity<GetWalletBalance> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(balanceApiUri)
                        .queryParam("user_id", userId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GetWalletBalance.class).block();

        if(response == null || response.getBody() == null)
            throw new WalletBalanceInquiryException(userId);

        return response.getBody().toUser();
    }
}
