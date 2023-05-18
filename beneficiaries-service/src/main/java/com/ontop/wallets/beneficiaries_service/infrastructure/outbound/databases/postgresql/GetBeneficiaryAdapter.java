package com.ontop.wallets.beneficiaries_service.infrastructure.outbound.databases.postgresql;

import com.ontop.wallets.beneficiaries_service.common.exceptions.custom.BeneficiaryNotFoundException;
import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;
import com.ontop.wallets.beneficiaries_service.domain.ports.outbound.GetBeneficiaryPort;
import com.ontop.wallets.beneficiaries_service.infrastructure.outbound.databases.postgresql.repositories.BeneficiariesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetBeneficiaryAdapter implements GetBeneficiaryPort {

    private final BeneficiariesRepository beneficiariesRepository;

    @Override
    public Optional<Beneficiary> getBeneficiary(Long id) {
        Optional<Beneficiary> beneficiaryOptional = beneficiariesRepository.findById(id);
        if(beneficiaryOptional.isPresent())
            return beneficiaryOptional;
        else
            throw new BeneficiaryNotFoundException(id.toString());
    }
}
