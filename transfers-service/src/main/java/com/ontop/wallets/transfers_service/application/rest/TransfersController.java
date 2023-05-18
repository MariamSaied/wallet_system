package com.ontop.wallets.transfers_service.application.rest;

import com.ontop.wallets.transfers_service.application.rest.request.TransferRequest;
import com.ontop.wallets.transfers_service.infrastructure.integration.rest.responses.TransferResponse;
import com.ontop.wallets.transfers_service.application.rest.response.RestResponseEntity;
import com.ontop.wallets.transfers_service.domain.ports.inbound.DoTransferUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.base.uri}")
@Validated
@RequiredArgsConstructor
public class TransfersController {

    private final DoTransferUseCase doTransferUseCase;

    @PostMapping(value = "/transfers/transfer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponseEntity<TransferResponse>> doTransfer(@RequestBody TransferRequest request) {

        TransferResponse transferResponse = doTransferUseCase.doTransfer(request);

        RestResponseEntity<TransferResponse> response = new RestResponseEntity<>(HttpStatus.OK.value(),
               HttpStatus.OK.getReasonPhrase(),transferResponse);

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
}
