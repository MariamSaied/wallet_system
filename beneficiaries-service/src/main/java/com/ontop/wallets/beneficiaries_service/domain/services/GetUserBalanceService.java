package com.ontop.wallets.beneficiaries_service.domain.services;

import com.ontop.wallets.beneficiaries_service.common.exceptions.custom.BeneficiaryNotFoundException;
import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;
import com.ontop.wallets.beneficiaries_service.domain.ports.inbound.GetBeneficiaryUseCase;
import com.ontop.wallets.beneficiaries_service.domain.ports.outbound.GetBeneficiaryPort;
import com.ontop.wallets.wallets_service.common.annotations.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class GetUserBalanceService implements GetBeneficiaryUseCase {

    private final GetBeneficiaryPort getBeneficiaryPort;

    @Override
    public Beneficiary getBeneficiary(Long beneficiaryId) {
        Optional<Beneficiary> beneficiaryOptional = getBeneficiaryPort.getBeneficiary(beneficiaryId);

        if(beneficiaryOptional.isPresent())
            return beneficiaryOptional.get();
        else throw new BeneficiaryNotFoundException(beneficiaryId.toString());
    }
}
