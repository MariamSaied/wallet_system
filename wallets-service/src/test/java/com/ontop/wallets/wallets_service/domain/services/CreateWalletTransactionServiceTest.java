package com.ontop.wallets.wallets_service.domain.services;

import com.ontop.wallets.wallets_service.common.enums.Operation;
import com.ontop.wallets.wallets_service.common.exceptions.custom.InsufficientFundsException;
import com.ontop.wallets.wallets_service.domain.model.Wallet;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import com.ontop.wallets.wallets_service.domain.ports.outbound.CreateWalletTransactionPort;
import com.ontop.wallets.wallets_service.domain.ports.outbound.GetWalletBalancePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreateWalletTransactionServiceTest {

    @Autowired
    private CreateWalletTransactionService createWalletTransactionService;

    @MockBean
    private CreateWalletTransactionPort createWalletTransactionPort;

    @MockBean
    private GetWalletBalancePort getWalletBalancePort;

    @Test
    public void createWalletTransaction_TopUpWalletTransaction_WalletTransactionSuccessfullySubmittedWithCorrectBalances(){

        WalletTransaction walletTransactionRequest =
                new WalletTransaction(null,1L, BigDecimal.valueOf(200),null,
                        null, Operation.TOPUP);

        Wallet wallet = new Wallet(walletTransactionRequest.getUserId(),BigDecimal.valueOf(800));

        WalletTransaction expectedWalletTransaction =
                new WalletTransaction(34893L,1L, BigDecimal.valueOf(200),BigDecimal.valueOf(800),
                        BigDecimal.valueOf(1000), Operation.TOPUP);

        when(getWalletBalancePort.getWalletBalance(any(Long.class))).thenReturn(wallet);

        when(createWalletTransactionPort.createWalletTransaction(any(WalletTransaction.class)))
                .thenReturn(expectedWalletTransaction);

        WalletTransaction actualWalletTransactionResponse =
                createWalletTransactionService.createWalletTransaction(walletTransactionRequest);

        assertThat(actualWalletTransactionResponse).usingRecursiveComparison().isEqualTo(expectedWalletTransaction);

    }

    @Test
    public void createWalletTransaction_WithdrawalWalletTransactionWithEnoughBalance_WalletTransactionSuccessfullySubmittedWithCorrectBalances(){

        WalletTransaction walletTransactionRequest =
                new WalletTransaction(null,1L, BigDecimal.valueOf(200),null,
                        null, Operation.WITHDRAW);

        Wallet wallet = new Wallet(walletTransactionRequest.getUserId(),BigDecimal.valueOf(1000));

        WalletTransaction expectedWalletTransaction =
                new WalletTransaction(34893L,1L, BigDecimal.valueOf(200),BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(800), Operation.WITHDRAW);

        when(getWalletBalancePort.getWalletBalance(any(Long.class))).thenReturn(wallet);

        when(createWalletTransactionPort.createWalletTransaction(any(WalletTransaction.class)))
                .thenReturn(expectedWalletTransaction);

        WalletTransaction actualWalletTransactionResponse =
                createWalletTransactionService.createWalletTransaction(walletTransactionRequest);

        assertThat(actualWalletTransactionResponse).usingRecursiveComparison().isEqualTo(expectedWalletTransaction);

    }

    @Test
    public void createWalletTransaction_WithdrawalWalletTransactionWithInsufficientFunds_InsufficientFundsExceptionThrown(){

        WalletTransaction walletTransactionRequest =
                new WalletTransaction(null,1L, BigDecimal.valueOf(200),null,
                        null, Operation.WITHDRAW);

        Wallet wallet = new Wallet(walletTransactionRequest.getUserId(),BigDecimal.ZERO);

        WalletTransaction expectedExceptionBody =
                new WalletTransaction(null,1L, BigDecimal.valueOf(200),BigDecimal.ZERO,
                        null, Operation.WITHDRAW);

        when(getWalletBalancePort.getWalletBalance(any(Long.class))).thenReturn(wallet);

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> createWalletTransactionService.createWalletTransaction(walletTransactionRequest));

        assertThat(exception.getWalletTransaction()).usingRecursiveComparison().isEqualTo(expectedExceptionBody);

    }

}