package com.backend.domain.store.repository;

import com.backend.domain.store.entity.BusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessHourRepository extends JpaRepository<BusinessHour, Long> {

    List<BusinessHour> findByStoreStoreId(Long storeId);

}
