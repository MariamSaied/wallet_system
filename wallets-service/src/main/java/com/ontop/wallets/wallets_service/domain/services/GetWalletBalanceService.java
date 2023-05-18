package com.ontop.wallets.wallets_service.domain.services;

import com.ontop.wallets.wallets_service.common.annotations.UseCase;
import com.ontop.wallets.wallets_service.domain.model.Wallet;
import com.ontop.wallets.wallets_service.domain.ports.inbound.GetWalletBalanceUseCase;
import com.ontop.wallets.wallets_service.domain.ports.outbound.GetWalletBalancePort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetWalletBalanceService implements GetWalletBalanceUseCase {

    private final GetWalletBalancePort getWalletBalancePort;

    @Override
    public Wallet getWalletBalance(Long userId) {
        return getWalletBalancePort.getWalletBalance(userId);
    }
}
