package com.ontop.wallets.beneficiaries_service.application.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBeneficiaryRequest {
    private String firstName;
    private String lastName;
    private String routingNumber;
    private String nationalId;
    private String accountNumber;
    private String bankName;

//    public WalletTransaction toWalletTransaction(){
//        BigDecimal amount = this.operation == Operation.WITHDRAW ?
//                this.amount.negate() : this.amount;
//
//        return new WalletTransaction(null,this.userId,amount,operation);
//    }
}
