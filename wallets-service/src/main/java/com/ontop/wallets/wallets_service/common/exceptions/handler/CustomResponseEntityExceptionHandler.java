package com.ontop.wallets.wallets_service.common.exceptions.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.wallets.wallets_service.application.rest.responses.RestResponseEntity;
import com.ontop.wallets.wallets_service.common.exceptions.config.ErrorCodes;
import com.ontop.wallets.wallets_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.wallets_service.common.exceptions.custom.InsufficientFundsException;
import com.ontop.wallets.wallets_service.common.exceptions.custom.WalletBalanceInquiryException;
import com.ontop.wallets.wallets_service.common.exceptions.custom.WalletTransactionCreationException;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import com.ontop.wallets.wallets_service.infrastructure.integration.rest.responses.IntegrationErrorResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomResponseEntityExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Handles Insufficient Funds Business Case
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<RestResponseEntity<WalletTransaction>> handleInsufficientFundsException(InsufficientFundsException ex) {

        RestResponseEntity<WalletTransaction> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.INSUFFICIENT_FUNDS, ErrorMessages.INSUFFICIENT_FUNDS,
                        ex.getWalletTransaction());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseEntity);
    }

    // Handles Empty Response without Body from Gateway
    @ExceptionHandler(WalletTransactionCreationException.class)
    public ResponseEntity<RestResponseEntity<WalletTransaction>> handleWalletTransactionCreationException(WalletTransactionCreationException ex) {

        RestResponseEntity<WalletTransaction> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.WALLET_GATEWAY_FAILURE,
                        ErrorMessages.WALLET_TRANSACTION_FAILURE, ex.getWalletTransaction());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseEntity);
    }

    // Handles Successful Response without Body from Gateway
    @ExceptionHandler(WalletBalanceInquiryException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleWalletBalanceInquiryException(WalletBalanceInquiryException ex) {

        RestResponseEntity<Object> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.WALLET_GATEWAY_FAILURE, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseEntity);
    }

    // Handles Response Codes 5xx/4xx from Gateway
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<RestResponseEntity<IntegrationErrorResponseEntity>> handleWebClientResponseException(WebClientResponseException ex) throws JsonProcessingException {

        IntegrationErrorResponseEntity integrationErrorResponseEntity =
                objectMapper.readValue(ex.getResponseBodyAsString(), IntegrationErrorResponseEntity.class);

        RestResponseEntity<IntegrationErrorResponseEntity> errorResponseEntity =
                new RestResponseEntity<>(ex.getStatusCode().value(), integrationErrorResponseEntity.getMessage(),
                        integrationErrorResponseEntity);

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponseEntity);
    }

    // Handles Gateway Unreachable cases (timeout, service is down)
    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleWebClientRequestException() {

        RestResponseEntity<Object> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.WALLET_GATEWAY_UNREACHABLE,
                ErrorMessages.WALLET_GATEWAY_UNREACHABLE, null);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value()).body(errorResponseEntity);
    }

    // Handles any Failure might arise in Wallets Service
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponseEntity<Object>> handleException() {

        RestResponseEntity<Object> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.WALLET_SERVICE_FAILURE, ErrorMessages.WALLET_SERVICE_FAILURE, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponseEntity);
    }
}
