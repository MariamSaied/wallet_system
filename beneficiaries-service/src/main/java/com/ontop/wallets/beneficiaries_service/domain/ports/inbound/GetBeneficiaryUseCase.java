package com.ontop.wallets.beneficiaries_service.domain.ports.inbound;


import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;

public interface GetBeneficiaryUseCase {
    Beneficiary getBeneficiary(Long userId);
}
