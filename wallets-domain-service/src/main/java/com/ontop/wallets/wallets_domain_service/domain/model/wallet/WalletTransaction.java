package com.ontop.wallets.wallets_domain_service.domain.model.wallet;

import com.ontop.wallets.wallets_domain_service.common.enums.Operation;
import com.ontop.wallets.wallets_domain_service.domain.model.transfer.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletTransaction {
    private Long walletTransactionId;
    private Long userId;
    private BigDecimal amount;
    private Operation operation;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;

    public static WalletTransaction fromTransfer(Transfer transferRequest){
        return new WalletTransaction(null,transferRequest.getUserId(),
                transferRequest.getAmount(), Operation.WITHDRAW,null,null);
    }

    public void negateOperation(){
        this. operation = switch (this.operation){
            case TOPUP -> Operation.WITHDRAW;
            case WITHDRAW -> Operation.TOPUP;
        };
    }
}
