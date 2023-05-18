package com.ontop.wallets.wallets_service.domain.services;

import com.ontop.wallets.wallets_service.common.annotations.UseCase;
import com.ontop.wallets.wallets_service.common.enums.Operation;
import com.ontop.wallets.wallets_service.common.exceptions.custom.InsufficientFundsException;
import com.ontop.wallets.wallets_service.domain.model.Wallet;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import com.ontop.wallets.wallets_service.domain.ports.inbound.CreateWalletTransactionUseCase;
import com.ontop.wallets.wallets_service.domain.ports.outbound.CreateWalletTransactionPort;
import com.ontop.wallets.wallets_service.domain.ports.outbound.GetWalletBalancePort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateWalletTransactionService implements CreateWalletTransactionUseCase {

    private final CreateWalletTransactionPort createWalletTransactionPort;
    private final GetWalletBalancePort getWalletBalancePort;

    @Override
    public WalletTransaction createWalletTransaction(WalletTransaction walletTransaction) {

        // Get User Balance to authorise the Wallet Transaction
        Wallet wallet = getWalletBalancePort.getWalletBalance(walletTransaction.getUserId());

        walletTransaction.setBalanceBefore(wallet.getBalance());

        // Proceed with Transaction if the Operation is either TopUp or (Withdrawal and There are enough Funds)
        if(walletTransaction.getOperation() == Operation.TOPUP ||
                (walletTransaction.getOperation() == Operation.WITHDRAW && wallet.hasEnoughBalance(walletTransaction.getAmount()))){

            WalletTransaction walletTransactionResponse =
                    createWalletTransactionPort.createWalletTransaction(walletTransaction);

            // Set balance before and after by calculating balance before Transaction + Transaction amount
            walletTransactionResponse.setBalances(wallet.getBalance());
            return walletTransactionResponse;
        }
        else
            throw new InsufficientFundsException(walletTransaction);

    }
}
