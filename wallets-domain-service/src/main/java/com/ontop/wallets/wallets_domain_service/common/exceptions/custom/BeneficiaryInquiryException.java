package com.ontop.wallets.wallets_domain_service.common.exceptions.custom;

import com.ontop.wallets.wallets_domain_service.common.exceptions.config.ErrorMessages;

public class BeneficiaryInquiryException extends RuntimeException {

    public BeneficiaryInquiryException(Long beneficiaryId){
        super(ErrorMessages.BENEFICIARY_INQUIRY_EXCEPTION + beneficiaryId);
    }
}
