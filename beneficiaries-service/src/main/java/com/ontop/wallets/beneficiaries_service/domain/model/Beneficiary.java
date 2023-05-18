package com.ontop.wallets.beneficiaries_service.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Beneficiary {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long beneficiaryId;
    private String firstName;
    private String lastName;
    private String routingNumber;
    private String nationalId;
    private String accountNumber;
    private String bankName;
    @Column(unique = true)
    private Long userId;
}
