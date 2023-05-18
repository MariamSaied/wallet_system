package com.ontop.wallets.wallets_service.infrastructure.integration.rest;

import com.ontop.wallets.wallets_service.common.config.properties.WebClientProperties;
import com.ontop.wallets.wallets_service.common.exceptions.custom.WalletTransactionCreationException;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import com.ontop.wallets.wallets_service.domain.ports.outbound.CreateWalletTransactionPort;
import com.ontop.wallets.wallets_service.infrastructure.integration.rest.requests.CreateWalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CreateWalletTransactionAdapter implements CreateWalletTransactionPort {

    private final WebClient webClient;
    private final WebClientProperties webClientProperties;
    private final String walletServiceBaseUri;
    private final String walletServiceApiPrefix;

    @Autowired
    public CreateWalletTransactionAdapter(WebClient webClient, WebClientProperties webClientProperties){
        this.webClient = webClient;
        this.webClientProperties = webClientProperties;
        this.walletServiceBaseUri = webClientProperties.getServices().getWalletService().getBaseUri();
        this.walletServiceApiPrefix = webClientProperties.getServices().getWalletService().getApiPrefix();
    }

    @Override
    public WalletTransaction createWalletTransaction(WalletTransaction walletTransaction) {

        String createTransactionUri = walletServiceBaseUri + walletServiceApiPrefix +
                webClientProperties.getServices().getWalletService().getCreateTransactionApi();

        ResponseEntity<CreateWalletTransaction> response = webClient.post()
                .uri(createTransactionUri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(CreateWalletTransaction.fromWalletTransaction(walletTransaction))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(CreateWalletTransaction.class).block();

        if(response == null || response.getBody() == null)
            throw new WalletTransactionCreationException(walletTransaction);

        return response.getBody().toWalletTransaction();

    }
}
