package com.ontop.wallets.wallets_domain_service.domain.services;

import com.ontop.wallets.wallets_domain_service.application.rest.responses.TransferResponse;
import com.ontop.wallets.wallets_domain_service.common.annotations.UseCase;
import com.ontop.wallets.wallets_domain_service.common.exceptions.custom.TransferPlacementException;
import com.ontop.wallets.wallets_domain_service.domain.model.transfer.Transfer;
import com.ontop.wallets.wallets_domain_service.domain.ports.inbound.DoTransferUseCase;
import com.ontop.wallets.wallets_domain_service.domain.ports.outbound.BeneficiariesPort;
import com.ontop.wallets.wallets_domain_service.domain.ports.outbound.TransfersPort;
import com.ontop.wallets.wallets_domain_service.domain.ports.outbound.WalletsPort;
import com.ontop.wallets.wallets_domain_service.domain.model.beneficiary.Beneficiary;
import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests.TransferRequest;
import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.responses.TransferServiceResponse;
import com.ontop.wallets.wallets_domain_service.domain.model.wallet.WalletTransaction;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DoTransferService implements DoTransferUseCase {

    private final TransfersPort transfersPort;
    private final WalletsPort walletsPort;
    private final BeneficiariesPort beneficiariesPort;

    @Override
    public TransferResponse doTransfer(Transfer transfer) {
        TransferServiceResponse transferResponse;

        // Get Beneficiary/Bank Account Details
        Beneficiary beneficiary =
                beneficiariesPort.getBeneficiary(transfer.getBeneficiaryId());

        // Withdraw Transaction Amount from the Wallet
        WalletTransaction walletTransaction =
                walletsPort.createWalletTransaction(WalletTransaction.fromTransfer(transfer));

        // Do actual Transfer from OnTop Account to Beneficiary. In case of Failure -> TopUp Wallet
        try{
            TransferRequest transferRequest = TransferRequest.fromTransferAndBeneficiary(transfer,beneficiary);

            transferResponse =
                    transfersPort.doTransfer(transferRequest);

            if(transferResponse == null)
                throw new TransferPlacementException(transferRequest);

        } catch(TransferPlacementException ex){
            // Change Operation to be TopUp instead of Withdrawal
            walletTransaction.negateOperation();

            // TopOp User Wallet
            walletsPort.createWalletTransaction(walletTransaction);

            // Rethrow exception to be handled in ResponseEntityExceptionHandler
            throw ex;
        }

        return TransferResponse.builder().userId(transfer.getUserId())
                .beneficiaryId(transfer.getBeneficiaryId())
                .amount(transferResponse.getPaymentInfo().getAmount()).walletTransaction(walletTransaction)
                .transfer(transferResponse).build();
    }
}
