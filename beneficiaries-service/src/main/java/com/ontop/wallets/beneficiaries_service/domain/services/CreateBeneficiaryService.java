package com.ontop.wallets.beneficiaries_service.domain.services;

import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;
import com.ontop.wallets.beneficiaries_service.domain.ports.inbound.CreateBeneficiaryUseCase;
import com.ontop.wallets.beneficiaries_service.domain.ports.outbound.CreateBeneficiaryPort;
import com.ontop.wallets.wallets_service.common.annotations.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateBeneficiaryService implements CreateBeneficiaryUseCase {

    private final CreateBeneficiaryPort createBeneficiaryPort;

    @Override
    public Beneficiary createBeneficiary(Beneficiary beneficiary) {
        return createBeneficiaryPort.createBeneficiary(beneficiary);
    }
}
