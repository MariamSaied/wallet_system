package com.ontop.wallets.beneficiaries_service.application.rest;

import com.ontop.wallets.beneficiaries_service.application.rest.response.RestResponseEntity;
import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;
import com.ontop.wallets.beneficiaries_service.domain.ports.inbound.CreateBeneficiaryUseCase;
import com.ontop.wallets.beneficiaries_service.domain.ports.inbound.GetBeneficiaryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.base.uri}")
@RequiredArgsConstructor
public class BeneficiariesController {

    private final CreateBeneficiaryUseCase createBeneficiaryUseCase;
    private final GetBeneficiaryUseCase getBeneficiaryUseCase;

    @GetMapping("beneficiaries/beneficiary/{id}")
    public ResponseEntity<RestResponseEntity<Beneficiary>> getBeneficiary(@PathVariable Long id) {

        Beneficiary getBeneficiaryResponse = getBeneficiaryUseCase.getBeneficiary(id);

        RestResponseEntity<Beneficiary> response = new RestResponseEntity<>(HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),getBeneficiaryResponse);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/beneficiaries/beneficiary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponseEntity<Beneficiary>> createBeneficiary(@RequestBody Beneficiary request) {

        Beneficiary createBeneficiaryResponse = createBeneficiaryUseCase.createBeneficiary(request);

        RestResponseEntity<Beneficiary> response = new RestResponseEntity<>(HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),createBeneficiaryResponse);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }
}
