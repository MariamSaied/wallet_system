package com.ontop.wallets.beneficiaries_service.common.exceptions.custom;

import com.ontop.wallets.beneficiaries_service.common.exceptions.config.ErrorMessages;

public class BeneficiaryNotFoundException extends RuntimeException {
    public BeneficiaryNotFoundException(String beneficiaryId){
        super(ErrorMessages.BENEFICIARY_NOT_FOUND + beneficiaryId);
    }
}
