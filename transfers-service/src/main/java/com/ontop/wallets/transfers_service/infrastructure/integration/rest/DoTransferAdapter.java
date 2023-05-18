package com.ontop.wallets.transfers_service.infrastructure.integration.rest;


import com.ontop.wallets.transfers_service.common.config.properties.webclient.WebClientProperties;
import com.ontop.wallets.transfers_service.common.exceptions.custom.TransferPlacementException;
import com.ontop.wallets.transfers_service.domain.model.Transfer;
import com.ontop.wallets.transfers_service.domain.ports.outbound.DoTransferPort;
import com.ontop.wallets.transfers_service.infrastructure.integration.rest.responses.TransferResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DoTransferAdapter implements DoTransferPort {

    private final WebClient webClient;
    private final WebClientProperties webClientProperties;
    private final String transferServiceApiPrefix;

    @Autowired
    public DoTransferAdapter(WebClient webClient, WebClientProperties webClientProperties){
        this.webClient = webClient;
        this.webClientProperties = webClientProperties;
        this.transferServiceApiPrefix = webClientProperties.getServices().getTransferService().getApiPrefix();
    }

    @Override
    public TransferResponse doTransfer(Transfer transfer) {
        String doTransferUri = transferServiceApiPrefix +
                webClientProperties.getServices().getTransferService().getDoTransferApi();

        ResponseEntity<TransferResponse> response = webClient.post()
                .uri(doTransferUri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transfer)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(TransferResponse.class).block();

        if(response == null || response.getBody() == null)
            throw new TransferPlacementException(transfer);

        return response.getBody();

    }
}
