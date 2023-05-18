package com.ontop.wallets.beneficiaries_service.infrastructure.outbound.databases.postgresql.repositories;

import com.ontop.wallets.beneficiaries_service.domain.model.Beneficiary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiariesRepository extends CrudRepository<Beneficiary,Long> {
        Beneficiary save(Beneficiary beneficiary);
        Optional<Beneficiary> findById(Long beneficiaryId);
}