package com.ontop.wallets.transfers_service.common.exceptions.handling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.wallets.transfers_service.application.rest.response.RestResponseEntity;
import com.ontop.wallets.transfers_service.common.exceptions.config.ErrorCodes;
import com.ontop.wallets.transfers_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.transfers_service.common.exceptions.custom.TransferPlacementException;
import com.ontop.wallets.transfers_service.domain.model.Transfer;
import com.ontop.wallets.transfers_service.infrastructure.integration.rest.responses.TransferResponse;
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
    public CustomResponseEntityExceptionHandler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    // Handles Successful Response without Body from Gateway
    @ExceptionHandler(TransferPlacementException.class)
    public ResponseEntity<RestResponseEntity<Transfer>> handleTransferPlacementException(TransferPlacementException ex) {

        RestResponseEntity<Transfer> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.TRANSFERS_GATEWAY_FAILURE,ex.getMessage(),ex.getTransfer());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseEntity);
    }

    // Handles Response Codes 5xx/4xx from Gateway
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<RestResponseEntity<TransferResponse>> handleWebClientResponseException(WebClientResponseException ex) throws JsonProcessingException {

        TransferResponse integrationError =
                objectMapper.readValue(ex.getResponseBodyAsString(), TransferResponse.class);

        RestResponseEntity<TransferResponse> errorResponseEntity =
                new RestResponseEntity<>(ex.getStatusCode().value(),integrationError.getRequestInfo().getError(), integrationError);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponseEntity);
    }

    // Handles Gateway Unreachable cases (timeout, service is down)
    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleWebClientRequestException() {

        RestResponseEntity<Object> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.TRANSFERS_GATEWAY_UNREACHABLE,
                ErrorMessages.TRANSFER_GATEWAY_UNREACHABLE, null);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value()).body(errorResponseEntity);
    }

    // Handles any Failure might arise in Transfers Service
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponseEntity<Object>> handleException() {

        RestResponseEntity<Object> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.TRANSFERS_SERVICE_FAILURE, ErrorMessages.TRANSFERS_SERVICE_FAILURE, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponseEntity);
    }

}
