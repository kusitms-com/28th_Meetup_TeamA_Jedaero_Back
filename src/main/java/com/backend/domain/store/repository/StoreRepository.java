package com.backend.domain.store.repository;

import com.backend.domain.store.dto.StoreDetailsDto;
import com.backend.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(
            value =
                    "SELECT" +
                    "       s.store_id AS storeId," +
                    "       s.name as storeName," +
                    "       s.description as description," +
                    "       IF(p.pick_id IS NOT NULL, 'true', 'false') AS isPicked," +
                    "       s.category as category," +
                    "       s.address as address," +
                    "       s.phone_number as phoneNumber," +
                    "       ST_Distance_Sphere(point(s.longitude, s.latitude), point(:longitude, :latitude)) as distance," +
                    "       s.map_url as mapUrl" +
                    "  FROM store s" +
                    "  LEFT JOIN pick p ON s.store_id = p.store_id AND p.user_id = :userId" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.store_id = :storeId",
            nativeQuery = true
    )
    Optional<StoreDetailsDto> findStoreDetailById(@Param("userId") Long userId,
                                                  @Param("storeId") Long storeId,
                                                  @Param("latitude") Double latitude,
                                                  @Param("longitude") Double longitude);

}
