package com.backend.domain.contract.repository;

import com.backend.domain.contract.entity.Contract;
import com.backend.domain.store.entity.Category;
import com.backend.domain.store.entity.Store;
import com.backend.domain.university.entity.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c JOIN c.university u JOIN u.users user " +
            "WHERE user.id = :userId AND c.store.storeId = :storeId")
    Optional<Contract> findContractByUserIdAndStoreId(@Param("userId") Long userId, @Param("storeId") Long storeId);

    @Query(
            value =
                    "select c" +
                            "  from Contract c" +
                            " where c.university = :university " +
                            "         and c.store.name" +
                            "         like :storeName" +
                            "         and now() between c.startDate and c.endDate",
            countQuery =
                    "select count(distinct c)" +
                            "  from Contract c" +
                            " where c.university = :university " +
                            "         and c.store.name " +
                            "         like :storeName" +
                            "         and now() between c.startDate and c.endDate")
    Page<Contract> findAllByUniversityAndStoreName(@Param(value = "university") University university,
                                                   @Param(value = "storeName") String storeName,
                                                   PageRequest page);

    @Query(
            value =
                    "select c" +
                            "  from Contract c" +
                            " where c.university = :university" +
                            "         and c.store.name" +
                            "         like :storeName" +
                            "         and c.store.category = :category" +
                            "         and now() between c.startDate and c.endDate",
            countQuery =
                    "select count(distinct c)" +
                            "  from Contract c" +
                            " where c.university = :university " +
                            "         and c.store.name " +
                            "         like :storeName " +
                            "         and c.store.category = :category" +
                            "         and now() between c.startDate and c.endDate")
    Page<Contract> findAllByUniversityAndStoreNameAndCategory(@Param(value = "university") University university,
                                                              @Param(value = "storeName") String storeName,
                                                              @Param(value = "category") Category category,
                                                              PageRequest page);

    Optional<Contract> findByUniversityAndStore(University university, Store store);

    List<Contract> findAllByUniversity(University university);

}