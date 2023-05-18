package com.ontop.wallets.beneficiaries_service.common.exceptions.handling;

import com.ontop.wallets.beneficiaries_service.application.rest.response.RestResponseEntity;
import com.ontop.wallets.beneficiaries_service.common.exceptions.config.ErrorCodes;
import com.ontop.wallets.beneficiaries_service.common.exceptions.config.ErrorMessages;
import com.ontop.wallets.beneficiaries_service.common.exceptions.custom.BeneficiaryCreationPersistenceError;
import com.ontop.wallets.beneficiaries_service.common.exceptions.custom.BeneficiaryNotFoundException;
import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Handles receiving non-existing beneficiary id
    @ExceptionHandler(BeneficiaryNotFoundException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleBeneficiaryNotFoundException(BeneficiaryNotFoundException ex) {

        RestResponseEntity<Object> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.BENEFICIARY_NOT_FOUND,ex.getMessage(),null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseEntity);
    }

    // Handles Null Pointer Exception when CrudRepository save() returns null
    @ExceptionHandler(BeneficiaryCreationPersistenceError.class)
    public ResponseEntity<RestResponseEntity<Beneficiary>> handleBeneficiaryCreationPersistenceError(BeneficiaryCreationPersistenceError ex) {

        RestResponseEntity<Beneficiary> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.BENEFICIARY_CREATION_PERSISTENCE_ERROR,ex.getMessage(),ex.getBeneficiary());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseEntity);
    }

    // Handles Data Integrity Failures (I.E Violating Constraints)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        String message = ex.getCause().getCause().getMessage().split("Detail: ")[1];
        RestResponseEntity<Object> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.BENEFICIARY_DATA_INTEGRITY_VIOLATION_FAILURE,message,null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseEntity);
    }

    // Handles Database Down/Access Exceptions
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<RestResponseEntity<Object>> handleJDBCConnectionException(CannotCreateTransactionException ex) {

        RestResponseEntity<Object> errorResponseEntity = new RestResponseEntity<>(ErrorCodes.BENEFICIARIES_SERVICE_FAILURE,ErrorMessages.BENEFICIARIES_DATA_ACCESS_EXCEPTION,null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseEntity);
    }

    // Handles any Failure might arise in Beneficiaries Service
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponseEntity<Object>> handleException(Exception ex) {

        RestResponseEntity<Object> errorResponseEntity =
                new RestResponseEntity<>(ErrorCodes.BENEFICIARIES_SERVICE_FAILURE, ErrorMessages.BENEFICIARIES_SERVICE_FAILURE, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponseEntity);
    }
}
