package com.backend.domain.contract.repository;

import com.backend.domain.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c JOIN c.university u JOIN u.user user " +
            "WHERE user.id = :userId AND c.store.storeId = :storeId")
    Optional<Contract> findContractByUserIdAndStoreId(@Param("userId") Long userId, @Param("storeId") Long storeId);
}