package com.ontop.wallets.wallets_domain_service.common.exceptions.handling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.wallets.wallets_domain_service.application.rest.responses.RestResponseEntity;
import com.ontop.wallets.wallets_domain_service.common.exceptions.config.ErrorCodes;
import com.ontop.wallets.wallets_domain_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.wallets_domain_service.common.exceptions.custom.*;
import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests.TransferRequest;
import com.ontop.wallets.wallets_domain_service.domain.model.wallet.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomResponseEntityExceptionHandler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    // Handles Successful Response without Body from Wallets Service
    @ExceptionHandler(WalletTransactionCreationException.class)
    public ResponseEntity<RestResponseEntity<WalletTransaction>> handleWalletTransactionCreationException(WalletTransactionCreationException ex) {

        RestResponseEntity<WalletTransaction> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.WALLETS_SERVICE_FAILURE,ex.getMessage(),ex.getWalletTransaction());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseEntity);
    }

    // Handles Successful Response without Body from Transfers Service
    @ExceptionHandler(TransferPlacementException.class)
    public ResponseEntity<RestResponseEntity<TransferRequest>> handleTransferPlacementException(TransferPlacementException ex) {

        RestResponseEntity<TransferRequest> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.TRANSFERS_SERVICE_FAILURE,ex.getMessage(),ex.getTransferRequest());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseEntity);
    }

    // Handles Successful Response without Body from Beneficiaries Service
    @ExceptionHandler(BeneficiaryInquiryException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleBeneficiaryInquiryException(BeneficiaryInquiryException ex) {

        RestResponseEntity<Object> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.BENEFICIARIES_SERVICE_FAILURE,ex.getMessage(),null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseEntity);
    }

    // Handles Wallets Service Unreachable Exception
    @ExceptionHandler(WalletsServiceUnreachableException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleWalletsServiceUnreachableException() {

        RestResponseEntity<Object> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.WALLETS_SERVICE_UNREACHABLE, ErrorMessages.WALLETS_SERVICE_UNREACHABLE, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponseEntity);
    }

    // Handles Transfers Service Unreachable Exception
    @ExceptionHandler(TransfersServiceUnreachableException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleTransfersServiceUnreachableException() {

        RestResponseEntity<Object> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.TRANSFERS_SERVICE_UNREACHABLE, ErrorMessages.TRANSFERS_SERVICE_UNREACHABLE, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponseEntity);
    }

    // Handles Beneficiaries Service Unreachable Exception
    @ExceptionHandler(BeneficiariesServiceUnreachableException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleBeneficiariesServiceUnreachableException() {

        RestResponseEntity<Object> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.BENEFICIARIES_SERVICE_UNREACHABLE, ErrorMessages.BENEFICIARIES_SERVICE_UNREACHABLE, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponseEntity);
    }

    // Handles Response Codes 5xx/4xx from Services
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<RestResponseEntity<RestResponseEntity<Object>>> handleWebClientResponseException(WebClientResponseException ex) throws JsonProcessingException {

        RestResponseEntity<Object> restResponseEntity =
                objectMapper.readValue(ex.getResponseBodyAsString(), new TypeReference<>() {
                });

        RestResponseEntity<RestResponseEntity<Object>> errorResponseEntity = new RestResponseEntity<>(restResponseEntity.getCode(), restResponseEntity.getMessage(), restResponseEntity);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponseEntity);
    }

    // Handles any Failure might arise in Wallets Domain Service
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponseEntity<Object>> handleException() {

        RestResponseEntity<Object> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.WALLETS_DOMAIN_SERVICE_FAILURE, ErrorMessages.WALLETS_DOMAIN_SERVICE_FAILURE, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponseEntity);
    }

}
