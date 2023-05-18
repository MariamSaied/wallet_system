package com.ontop.wallets.wallets_domain_service.domain.model.beneficiary;

import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests.Account;
import com.ontop.wallets.wallets_domain_service.infrastructure.integration.rest.requests.DestinationAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Beneficiary {
    private Long beneficiaryId;
    private String firstName;
    private String lastName;
    private String routingNumber;
    private String nationalId;
    private String accountNumber;
    private String bankName;
    private Long userId;

    public DestinationAccount toDestinationAccount(){
        return new DestinationAccount(firstName + " " + lastName,
                new Account(accountNumber, Currency.getInstance("USD"), routingNumber));
    }
}
