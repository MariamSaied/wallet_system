package com.ontop.wallets.wallets_service.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.wallets.wallets_service.application.rest.requests.CreateWalletTransactionRequest;
import com.ontop.wallets.wallets_service.application.rest.responses.RestResponseEntity;
import com.ontop.wallets.wallets_service.common.enums.Operation;
import com.ontop.wallets.wallets_service.common.exceptions.config.ErrorCodes;
import com.ontop.wallets.wallets_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.wallets_service.common.exceptions.custom.InsufficientFundsException;
import com.ontop.wallets.wallets_service.common.exceptions.custom.WalletBalanceInquiryException;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import com.ontop.wallets.wallets_service.domain.ports.inbound.CreateWalletTransactionUseCase;
import com.ontop.wallets.wallets_service.domain.ports.inbound.GetWalletBalanceUseCase;
import com.ontop.wallets.wallets_service.infrastructure.integration.rest.responses.IntegrationErrorResponseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WalletsControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final CreateWalletTransactionRequest createTransactionRequest =
            new CreateWalletTransactionRequest(BigDecimal.valueOf(200), 1L, Operation.WITHDRAW);

    @Value("${app.base.uri}")
    private String serviceBaseUri;

    @Autowired
    public WalletsControllerTest(MockMvc mockMvc, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @MockBean
    private CreateWalletTransactionUseCase createWalletTransactionUseCase;

    @MockBean
    private GetWalletBalanceUseCase getWalletBalanceUseCase;

    /* TODO
        Interface Validations should be added and tested */

    // Success Scenario Tests
    @Test
    public void createWalletTransaction_SuccessfulResponseFromGateway_SuccessfulResponseReturnedWithClientResponse() throws Exception
    {
        WalletTransaction walletTransactionResponse =
                new WalletTransaction(2329L,1L,BigDecimal.valueOf(200),BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(800),Operation.WITHDRAW);

        RestResponseEntity<WalletTransaction> response =
                new RestResponseEntity<>(HttpStatus.CREATED.value(),HttpStatus.CREATED.getReasonPhrase(),walletTransactionResponse);

        when(createWalletTransactionUseCase.createWalletTransaction(any(WalletTransaction.class)))
                .thenReturn(walletTransactionResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(serviceBaseUri + "/wallets/wallet/transaction")
                        .content(objectMapper.writeValueAsString(createTransactionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    // Integration Exceptions Tests
    @Test
    public void createWalletTransaction_BedRequestReturnedFromGateway_FormattedResponseReturnedAndCodeAndMessagePopulated() throws Exception
    {

        IntegrationErrorResponseEntity errorBody =
                new IntegrationErrorResponseEntity(ErrorCodes.WALLET_TRANSACTION_FAILURE.toString(),ErrorMessages.WALLET_TRANSACTION_FAILURE);

        WebClientResponseException ex =
                new WebClientResponseException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.WALLET_TRANSACTION_FAILURE,null,objectMapper.writeValueAsBytes(errorBody),null);

        RestResponseEntity<IntegrationErrorResponseEntity> response =
                new RestResponseEntity<>(ex.getStatusCode().value(),ex.getStatusText(),errorBody);

        when(createWalletTransactionUseCase.createWalletTransaction(any(WalletTransaction.class)))
                .thenThrow(ex);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(serviceBaseUri + "/wallets/wallet/transaction")
                        .content(objectMapper.writeValueAsString(createTransactionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void createWalletTransaction_GatewayTimedout_FormattedResponseReturnedAndCodeAndMessagePopulated() throws Exception
    {

        RestResponseEntity<Object> response =
                new RestResponseEntity<>(ErrorCodes.WALLET_GATEWAY_UNREACHABLE, ErrorMessages.WALLET_GATEWAY_UNREACHABLE,null);

        when(createWalletTransactionUseCase.createWalletTransaction(any(WalletTransaction.class)))
                .thenThrow(WebClientRequestException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(serviceBaseUri + "/wallets/wallet/transaction")
                        .content(objectMapper.writeValueAsString(createTransactionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void getUserBalance_UserBalanceInquiryExceptionThrown_FormattedResponseReturnedWithCode500() throws Exception
    {

        WalletBalanceInquiryException ex = new WalletBalanceInquiryException(1L);

        RestResponseEntity<Object> response =
                new RestResponseEntity<>(ErrorCodes.WALLET_GATEWAY_FAILURE, ex.getMessage(), null);

        when(getWalletBalanceUseCase.getWalletBalance(any(Long.class)))
                .thenThrow(ex);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(serviceBaseUri + "/wallets/wallet/balance")
                        .param("user-id",String.valueOf(1)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    // Service Generic Exception Tests
    @Test
    public void createWalletTransaction_NullPointerExceptionThrown_FormattedResponseReturnedWith500HttpStatusCode() throws Exception
    {

        RestResponseEntity<Object> response =
                new RestResponseEntity<>(ErrorCodes.WALLET_SERVICE_FAILURE, ErrorMessages.WALLET_SERVICE_FAILURE, null);

        when(createWalletTransactionUseCase.createWalletTransaction(any(WalletTransaction.class)))
                .thenThrow(NullPointerException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(serviceBaseUri + "/wallets/wallet/transaction")
                        .content(objectMapper.writeValueAsString(createTransactionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    // Business Exceptions Tests
    @Test
    public void createWalletTransaction_InsufficientFundsExceptionThrown_FormattedResponseReturnedWith500HttpStatusCode() throws Exception
    {
        WalletTransaction walletTransaction =
                new WalletTransaction(null,1L,BigDecimal.valueOf(200),BigDecimal.valueOf(1000),
                        null,Operation.WITHDRAW);

        RestResponseEntity<Object> response =
                new RestResponseEntity<>(ErrorCodes.INSUFFICIENT_FUNDS, ErrorMessages.INSUFFICIENT_FUNDS, walletTransaction);

        InsufficientFundsException ex = new InsufficientFundsException(walletTransaction);

        when(createWalletTransactionUseCase.createWalletTransaction(any(WalletTransaction.class)))
                .thenThrow(ex);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(serviceBaseUri + "/wallets/wallet/transaction")
                        .content(objectMapper.writeValueAsString(createTransactionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

}