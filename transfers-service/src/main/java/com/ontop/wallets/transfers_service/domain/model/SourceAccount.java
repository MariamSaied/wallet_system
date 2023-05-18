package com.ontop.wallets.transfers_service.domain.model;

import com.ontop.wallets.transfers_service.common.enums.TransferSourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SourceAccount {
    private TransferSourceType type = TransferSourceType.COMPANY;
    private SourceAccountInformation sourceInformation;
    private Account account;
}
