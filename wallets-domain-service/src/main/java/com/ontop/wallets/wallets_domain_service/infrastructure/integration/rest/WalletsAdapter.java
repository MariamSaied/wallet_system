package com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest;

import com.ontop.wallets.wallets_domain_service.application.rest.responses.RestResponseEntity;
import com.ontop.wallets.wallets_domain_service.common.config.properties.webclient.WalletService;
import com.ontop.wallets.wallets_domain_service.common.config.properties.webclient.WebClientProperties;
import com.ontop.wallets.wallets_domain_service.common.exceptions.custom.WalletTransactionCreationException;
import com.ontop.wallets.wallets_domain_service.common.exceptions.custom.WalletsServiceUnreachableException;
import com.ontop.wallets.wallets_domain_service.domain.ports.outbound.WalletsPort;
import com.ontop.wallets.wallets_domain_service.domain.model.wallet.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Component
public class WalletsAdapter implements WalletsPort {

    private final WebClient webClient;
    private final WebClientProperties webClientProperties;
    private final String walletTransactionBaseUri;

    @Autowired
    public WalletsAdapter(WebClient webClient, WebClientProperties webClientProperties){
        this.webClient = webClient;
        this.webClientProperties = webClientProperties;
        WalletService walletServiceProperties = webClientProperties.getServices().getWalletService();
        this.walletTransactionBaseUri = walletServiceProperties.getBaseUri() + walletServiceProperties.getApiPrefix();
    }

    @Override
    public WalletTransaction createWalletTransaction(WalletTransaction walletTransaction) {

        String createWalletTransactionUri = walletTransactionBaseUri +
                webClientProperties.getServices().getWalletService().getCreateWalletTransactionApi();

        ResponseEntity<RestResponseEntity<WalletTransaction>> response;

        try{
            response = webClient.post()
                    .uri(createWalletTransactionUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(walletTransaction)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<RestResponseEntity<WalletTransaction>>() {
                    }).block();

            if(response == null || response.getBody() == null)
                throw new WalletTransactionCreationException(walletTransaction);

            return response.getBody().getBody();
        } catch(WebClientRequestException ex){
            throw new WalletsServiceUnreachableException();
        }

    }
}
