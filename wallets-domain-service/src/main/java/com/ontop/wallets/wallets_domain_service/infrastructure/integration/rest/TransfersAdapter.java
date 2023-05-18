package com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest;


import com.ontop.wallets.wallets_domain_service.application.rest.responses.RestResponseEntity;
import com.ontop.wallets.wallets_domain_service.common.config.properties.webclient.TransferService;
import com.ontop.wallets.wallets_domain_service.common.config.properties.webclient.WebClientProperties;
import com.ontop.wallets.wallets_domain_service.common.exceptions.custom.TransferPlacementException;
import com.ontop.wallets.wallets_domain_service.common.exceptions.custom.TransfersServiceUnreachableException;
import com.ontop.wallets.wallets_domain_service.domain.ports.outbound.TransfersPort;
import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests.TransferRequest;
import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.responses.TransferServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Component
public class TransfersAdapter implements TransfersPort {

    private final WebClient webClient;
    private final TransferService transferServiceProperties;

    @Autowired
    public TransfersAdapter(WebClient webClient, WebClientProperties webClientProperties){
        this.webClient = webClient;
        this.transferServiceProperties = webClientProperties.getServices().getTransferService();
    }

    @Override
    public TransferServiceResponse doTransfer(TransferRequest transferRequest) {
        String doTransferUri = transferServiceProperties.getBaseUri() + transferServiceProperties.getApiPrefix() +
                transferServiceProperties.getDoTransferApi();

        ResponseEntity<RestResponseEntity<TransferServiceResponse>> response;

        try{
            response = webClient.post()
                    .uri(doTransferUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(transferRequest)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<RestResponseEntity<TransferServiceResponse>>() {
                    }).block();

            if(response == null || response.getBody() == null)
                throw new TransferPlacementException(transferRequest);

            return response.getBody().getBody();

        } catch (WebClientRequestException ex) {
            throw new TransfersServiceUnreachableException();
        }

    }
}
