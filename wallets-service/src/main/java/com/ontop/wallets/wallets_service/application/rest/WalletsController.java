package com.ontop.wallets.wallets_service.application.rest;

import com.ontop.wallets.wallets_service.application.rest.requests.CreateWalletTransactionRequest;
import com.ontop.wallets.wallets_service.application.rest.responses.RestResponseEntity;
import com.ontop.wallets.wallets_service.domain.model.Wallet;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import com.ontop.wallets.wallets_service.domain.ports.inbound.CreateWalletTransactionUseCase;
import com.ontop.wallets.wallets_service.domain.ports.inbound.GetWalletBalanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.base.uri}")
@RequiredArgsConstructor
public class WalletsController {

    private final CreateWalletTransactionUseCase createWalletTransactionUseCase;
    private final GetWalletBalanceUseCase getWalletBalanceUseCase;

    @PostMapping(value = "/wallets/wallet/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponseEntity<WalletTransaction>> createWalletTransaction(@RequestBody CreateWalletTransactionRequest request) {

        WalletTransaction createWalletTransactionResponse =
                createWalletTransactionUseCase.createWalletTransaction(request.toWalletTransaction());

        RestResponseEntity<WalletTransaction> response = new RestResponseEntity<>(HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),createWalletTransactionResponse);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/wallets/wallet/balance")
    public ResponseEntity<RestResponseEntity<Wallet>> getWalletBalance(@RequestParam(name = "user-id") Long userId) {
        Wallet getWalletBalanceResponse = getWalletBalanceUseCase.getWalletBalance(userId);

        RestResponseEntity<Wallet> response = new RestResponseEntity<>(HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),getWalletBalanceResponse);

        return ResponseEntity.ok().body(response);
    }
}
