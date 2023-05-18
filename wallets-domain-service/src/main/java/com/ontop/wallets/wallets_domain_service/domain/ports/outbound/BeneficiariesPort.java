package com.ontop.wallets.wallets_domain_service.domain.ports.outbound;

import com.ontop.wallets.wallets_domain_service.domain.model.beneficiary.Beneficiary;

public interface BeneficiariesPort {
    Beneficiary getBeneficiary(Long beneficiaryId);
}
