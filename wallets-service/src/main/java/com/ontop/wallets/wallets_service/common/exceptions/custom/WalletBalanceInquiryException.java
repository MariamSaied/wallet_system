package com.ontop.wallets.wallets_service.common.exceptions.custom;

import com.ontop.wallets.wallets_service.common.exceptions.config.ErrorMessages;

public class WalletBalanceInquiryException extends RuntimeException{
    public WalletBalanceInquiryException(Long userId){
        super(ErrorMessages.BALANCE_INQUIRY_FAILURE + userId);
    }
}
