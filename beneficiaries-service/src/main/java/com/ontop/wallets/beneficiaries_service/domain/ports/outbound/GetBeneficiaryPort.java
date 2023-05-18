package com.ontop.wallets.beneficiaries_service.domain.ports.outbound;

import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;

import java.util.Optional;

public interface GetBeneficiaryPort {
    Optional<Beneficiary> getBeneficiary(Long id);
}
