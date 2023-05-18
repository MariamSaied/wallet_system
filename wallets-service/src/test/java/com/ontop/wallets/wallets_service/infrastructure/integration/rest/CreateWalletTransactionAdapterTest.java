package com.ontop.wallets.wallets_service.infrastructure.integration.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.wallets.wallets_service.common.config.properties.Services;
import com.ontop.wallets.wallets_service.common.config.properties.WalletService;
import com.ontop.wallets.wallets_service.common.config.properties.WebClientProperties;
import com.ontop.wallets.wallets_service.common.enums.Operation;
import com.ontop.wallets.wallets_service.common.exceptions.custom.WalletTransactionCreationException;
import com.ontop.wallets.wallets_service.domain.model.WalletTransaction;
import com.ontop.wallets.wallets_service.infrastructure.integration.rest.requests.CreateWalletTransaction;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.math.BigDecimal;

import static com.ontop.wallets.wallets_service.infrastructure.integration.rest.config.TestingConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateWalletTransactionAdapterTest {

    private CreateWalletTransactionAdapter createWalletTransactionAdapter;
    private static ObjectMapper objectMapper;
    private static MockWebServer mockServer;
    private static WebClientProperties webClientProperties;

    @AfterAll
    static void afterAll() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    void setup() {
        WebClient webClient = WebClient.builder().baseUrl(mockServer.url("").toString()).build();
        createWalletTransactionAdapter = new CreateWalletTransactionAdapter(webClient,
                webClientProperties);
    }

    @BeforeAll
    static void beforeAll() {
        mockServer = new MockWebServer();
        WalletService walletService = new WalletService(CREATE_WALLET_TRANSACTION_PATH,GET_WALLET_BALANCE,APIS_PREFIX);
        Services services = new Services(walletService);
        webClientProperties = new WebClientProperties(services,TIMEOUT);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createWalletTransaction_SuccessfulWalletTransactionReturnedFromGateway_TransactionReturnedPopulated() throws JsonProcessingException {

        WalletTransaction walletTransactionRequest =
                new WalletTransaction(null,1L, BigDecimal.valueOf(200),null,
                        null, Operation.TOPUP);

        CreateWalletTransaction respondedWalletTransaction =
                new CreateWalletTransaction(6678L,1L, BigDecimal.valueOf(200));

        mockServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(respondedWalletTransaction))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        WalletTransaction actualWalletTransaction =
                createWalletTransactionAdapter.createWalletTransaction(walletTransactionRequest);

        assertThat(actualWalletTransaction).usingRecursiveComparison().isEqualTo(respondedWalletTransaction.toWalletTransaction());

    }

    @Test
    public void createWalletTransaction_NullResponseEntityReturnedFromGateway_WalletTransactionCreationExceptionThrown() {

        WalletTransaction walletTransactionRequest =
                new WalletTransaction(null,1L, BigDecimal.valueOf(200),null,
                        null, Operation.TOPUP);

        mockServer.enqueue(new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        WalletTransactionCreationException exception = assertThrows(WalletTransactionCreationException.class,
                () -> createWalletTransactionAdapter.createWalletTransaction(walletTransactionRequest));

        Assertions.assertThat(exception.getWalletTransaction()).usingRecursiveComparison().isEqualTo(walletTransactionRequest);

    }

    @Test
    public void createWalletTransaction_EmptyBodyReturnedFromGateway_WalletTransactionCreationExceptionThrown() throws JsonProcessingException {

        WalletTransaction walletTransactionRequest =
                new WalletTransaction(null,1L, BigDecimal.valueOf(200),null,
                        null, Operation.TOPUP);

        mockServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(null))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        WalletTransactionCreationException exception = assertThrows(WalletTransactionCreationException.class,
                () -> createWalletTransactionAdapter.createWalletTransaction(walletTransactionRequest));

        Assertions.assertThat(exception.getWalletTransaction()).usingRecursiveComparison().isEqualTo(walletTransactionRequest);

    }


}