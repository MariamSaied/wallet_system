package com.ontop.wallets.transfers_service.common.config.properties.transfer;

import com.ontop.wallets.transfers_service.domain.model.SourceAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "transfer.source-account.ontop")
@NoArgsConstructor
@Getter
@Setter
public class SourceAccountInformationProperties extends SourceAccount {
}
