package com.ontop.wallets.transfers_service.domain.services;

import com.ontop.wallets.transfers_service.application.rest.request.TransferRequest;
import com.ontop.wallets.transfers_service.common.annotations.UseCase;
import com.ontop.wallets.transfers_service.common.config.properties.transfer.SourceAccountInformationProperties;
import com.ontop.wallets.transfers_service.domain.model.Transfer;
import com.ontop.wallets.transfers_service.domain.ports.inbound.DoTransferUseCase;
import com.ontop.wallets.transfers_service.domain.ports.outbound.DoTransferPort;
import com.ontop.wallets.transfers_service.infrastructure.integration.rest.responses.TransferResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@UseCase
@EnableConfigurationProperties(SourceAccountInformationProperties.class)
public class DoTransferService implements DoTransferUseCase {

    private final DoTransferPort doTransferPort;
    private final SourceAccountInformationProperties sourceAccount;

    @Autowired
    public DoTransferService(DoTransferPort doTransferPort, SourceAccountInformationProperties sourceAccount){
        this.doTransferPort = doTransferPort;
        this.sourceAccount = sourceAccount;
    }

    @Override
    public TransferResponse doTransfer(TransferRequest transferRequest) {

        Transfer transfer = Transfer.builder().amount(transferRequest.getAmount())
                .source(this.sourceAccount).destination(transferRequest.getBeneficiary()).build();

        return doTransferPort.doTransfer(transfer);

    }
}
