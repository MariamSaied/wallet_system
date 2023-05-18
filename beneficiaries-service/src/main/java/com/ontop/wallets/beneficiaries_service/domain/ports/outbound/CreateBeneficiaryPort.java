package com.ontop.wallets.beneficiaries_service.domain.ports.outbound;

import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;

public interface CreateBeneficiaryPort {
    Beneficiary createBeneficiary(Beneficiary beneficiary);
}
