package com.ontop.wallets.wallets_service.domain.services;

import com.ontop.wallets.wallets_service.domain.model.Wallet;
import com.ontop.wallets.wallets_service.domain.ports.outbound.GetWalletBalancePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetWalletBalanceServiceTest {

    @Autowired
    private GetWalletBalanceService getUserBalanceService;

    @MockBean
    private GetWalletBalancePort getWalletBalancePort;

    @Test
    public void getUserBalance_UserRetrievedFromGatewaySuccessfully_UserRetrieved(){

        Wallet expectedWallet =
                new Wallet(1L,BigDecimal.valueOf(100));

        when(getWalletBalancePort.getWalletBalance(any(Long.class))).thenReturn(expectedWallet);

        Wallet actualWallet =
                getWalletBalancePort.getWalletBalance(1L);

        assertThat(actualWallet).usingRecursiveComparison().isEqualTo(expectedWallet);

    }
}