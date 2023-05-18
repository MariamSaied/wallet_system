package com.ontop.wallets.wallets_service.infrastructure.integration.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.wallets.wallets_service.common.config.properties.Services;
import com.ontop.wallets.wallets_service.common.config.properties.WalletService;
import com.ontop.wallets.wallets_service.common.config.properties.WebClientProperties;
import com.ontop.wallets.wallets_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.wallets_service.common.exceptions.custom.WalletBalanceInquiryException;
import com.ontop.wallets.wallets_service.domain.model.Wallet;
import com.ontop.wallets.wallets_service.infrastructure.integration.rest.requests.GetWalletBalance;
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
class GetWalletBalanceAdapterTest {

    private GetWalletBalanceAdapter getWalletBalanceAdapter;
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
        getWalletBalanceAdapter = new GetWalletBalanceAdapter(webClient,
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
    public void getUserBalance_SuccessfulUserReturnedFromGateway_UserReturnedPopulated() throws JsonProcessingException {

        GetWalletBalance respondedWallet =
                new GetWalletBalance(1L,BigDecimal.valueOf(200));

        mockServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(respondedWallet))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        Wallet actualWallet =
                getWalletBalanceAdapter.getWalletBalance(respondedWallet.getUserId());

        assertThat(actualWallet).usingRecursiveComparison().isEqualTo(respondedWallet.toUser());

    }

    @Test
    public void getUserBalance_NullResponseEntityReturnedFromGateway_UserBalanceInquiryExceptionThrown() {

        mockServer.enqueue(new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        WalletBalanceInquiryException exception = assertThrows(WalletBalanceInquiryException.class,
                () -> getWalletBalanceAdapter.getWalletBalance(1L));

        Assertions.assertThat(exception.getMessage()).isEqualTo(ErrorMessages.BALANCE_INQUIRY_FAILURE + 1L);

    }

    @Test
    public void createWalletTransaction_EmptyBodyReturnedFromGateway_WalletTransactionCreationExceptionThrown() throws JsonProcessingException {

        mockServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(null))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        WalletBalanceInquiryException exception = assertThrows(WalletBalanceInquiryException.class,
                () -> getWalletBalanceAdapter.getWalletBalance(1L));

        Assertions.assertThat(exception.getMessage()).isEqualTo(ErrorMessages.BALANCE_INQUIRY_FAILURE + 1L);

    }
}