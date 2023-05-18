package com.ontop.wallets.beneficiaries_service.infrastructure.outbound.databases.postgresql;

import com.ontop.wallets.beneficiaries_service.common.exceptions.custom.BeneficiaryCreationPersistenceError;
import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;
import com.ontop.wallets.beneficiaries_service.domain.ports.outbound.CreateBeneficiaryPort;
import com.ontop.wallets.beneficiaries_service.infrastructure.outbound.databases.postgresql.repositories.BeneficiariesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateBeneficiaryAdapter implements CreateBeneficiaryPort {

    private final BeneficiariesRepository beneficiariesRepository;

    @Override
    public Beneficiary createBeneficiary(Beneficiary beneficiary) {
        Beneficiary savedBeneficiary = beneficiariesRepository.save(beneficiary);
        if(savedBeneficiary != null)
            return savedBeneficiary;
        else
            throw new BeneficiaryCreationPersistenceError(beneficiary);
    }
}
