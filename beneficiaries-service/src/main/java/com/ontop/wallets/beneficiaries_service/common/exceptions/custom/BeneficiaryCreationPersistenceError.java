package com.ontop.wallets.beneficiaries_service.common.exceptions.custom;

import com.ontop.wallets.beneficiaries_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryCreationPersistenceError extends RuntimeException{
    private Beneficiary beneficiary;
    public BeneficiaryCreationPersistenceError(Beneficiary beneficiary){
        super(ErrorMessages.BENEFICIARY_CREATION_PERSISTENCE_ERROR);
        this.beneficiary = beneficiary;
    }
}
