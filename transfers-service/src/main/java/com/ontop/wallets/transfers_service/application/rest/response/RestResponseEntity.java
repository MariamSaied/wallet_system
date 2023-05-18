package com.ontop.wallets.transfers_service.application.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponseEntity<T> {
    private int code;
    private String message;
    private T body;
}
