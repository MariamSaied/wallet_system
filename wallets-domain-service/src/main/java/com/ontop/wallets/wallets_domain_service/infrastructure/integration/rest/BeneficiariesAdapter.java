package com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest;

import com.ontop.wallets.wallets_domain_service.application.rest.responses.RestResponseEntity;
import com.ontop.wallets.wallets_domain_service.common.config.properties.webclient.BeneficiaryService;
import com.ontop.wallets.wallets_domain_service.common.config.properties.webclient.WebClientProperties;
import com.ontop.wallets.wallets_domain_service.common.exceptions.custom.BeneficiariesServiceUnreachableException;
import com.ontop.wallets.wallets_domain_service.common.exceptions.custom.BeneficiaryInquiryException;
import com.ontop.wallets.wallets_domain_service.domain.ports.outbound.BeneficiariesPort;
import com.ontop.wallets.wallets_domain_service.domain.model.beneficiary.Beneficiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class BeneficiariesAdapter implements BeneficiariesPort {

    private final WebClient webClient;
    private final BeneficiaryService beneficiaryServiceProperties;

    @Autowired
    public BeneficiariesAdapter(WebClient webClient, WebClientProperties webClientProperties){
        this.webClient = webClient;
        this.beneficiaryServiceProperties = webClientProperties.getServices().getBeneficiaryService();
    }

    @Override
    public Beneficiary getBeneficiary(Long beneficiaryId) {

        String getBeneficiaryUri = beneficiaryServiceProperties.getBaseUri() + beneficiaryServiceProperties.getApiPrefix() +
                beneficiaryServiceProperties.getGetBeneficiaryApi();

        UriComponents uriComponentsBuilder =
                UriComponentsBuilder.fromHttpUrl(getBeneficiaryUri).buildAndExpand(beneficiaryId);

        ResponseEntity<RestResponseEntity<Beneficiary>> response;

        try{

            response = webClient.get()
                    .uri(uriComponentsBuilder.toUriString())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<RestResponseEntity<Beneficiary>>() {
                    }).block();

            if(response == null || response.getBody() == null)
                throw new BeneficiaryInquiryException(beneficiaryId);

            return response.getBody().getBody();

        } catch (WebClientRequestException ex){
            throw new BeneficiariesServiceUnreachableException();
        }


    }

}
