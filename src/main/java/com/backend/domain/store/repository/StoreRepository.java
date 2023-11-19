package com.backend.domain.store.repository;

import com.backend.domain.store.dto.StoreDetailsDto;
import com.backend.domain.store.dto.StoresDto;
import com.backend.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(
            value =
                    "SELECT" +
                    "       s.store_id AS storeId," +
                    "       s.name AS storeName," +
                    "       s.description AS description," +
                    "       CASE WHEN p.pick_id IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END AS isPicked," +
                    "       s.category AS category," +
                    "       s.address AS address," +
                    "       s.phone_number AS phoneNumber," +
                    "       ST_Distance_Sphere(point(s.longitude, s.latitude), point(:longitude, :latitude)) AS distance," +
                    "       s.map_url AS mapUrl" +
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

    @Query(
            value =
                    "SELECT" +
                    "       s.store_id AS storeId," +
                    "       s.name AS storeName," +
                    "       s.description AS description," +
                    "       CASE WHEN p.pick_id IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END AS isPicked," +
                    "       s.category AS category," +
                    "       s.address AS address," +
                    "       ST_Distance_Sphere(point(s.longitude, s.latitude), point(:longitude, :latitude)) AS distance" +
                    "  FROM store s" +
                    "  JOIN pick p ON s.store_id = p.store_id AND p.user_id = :userId" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.name LIKE :name" +
                    " ORDER BY distance IS NULL, distance",
            countQuery =
                    "SELECT" +
                    "       count(s.store_id) AS totalElements" +
                    "  FROM store s" +
                    "  JOIN pick p ON s.store_id = p.store_id AND p.user_id = :userId" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.name LIKE :name",
            nativeQuery = true)
    Page<StoresDto> findAllContainsNameAndPicked(@Param("userId") Long userId,
                                                 @Param("longitude") Double longitude,
                                                 @Param("latitude") Double latitude,
                                                 @Param("name") String name,
                                                 Pageable pageable);

    @Query(
            value =
                    "SELECT" +
                    "        s.store_id AS storeId," +
                    "        s.name AS storeName," +
                    "        s.description AS description," +
                    "        CASE WHEN p.pick_id IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END AS isPicked," +
                    "        s.category AS category," +
                    "        s.address AS address," +
                    "        ST_Distance_Sphere(point(s.longitude, s.latitude), point(:longitude, :latitude)) AS distance" +
                    "  FROM store s" +
                    "  JOIN pick p ON s.store_id = p.store_id AND p.user_id = :userId" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.name LIKE :name AND s.category = :category" +
                    " ORDER BY distance IS NULL, distance",
            countQuery =
                    "SELECT" +
                    "       count(s.store_id) AS totalElements" +
                    "  FROM store s" +
                    "  JOIN pick p ON s.store_id = p.store_id AND p.user_id = :userId" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.name LIKE :name AND s.category = :category",
            nativeQuery = true)
    Page<StoresDto> findAllContainsNameAndPickedAndCategory(@Param("userId") Long userId,
                                                            @Param("longitude") Double longitude,
                                                            @Param("latitude") Double latitude,
                                                            @Param("name") String name,
                                                            @Param("category") String category,
                                                            Pageable pageable);

    @Query(
            value =
                    "SELECT" +
                    "       s.store_id AS storeId," +
                    "       s.name AS storeName," +
                    "       s.description AS description," +
                    "       CASE WHEN p.pick_id IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END AS isPicked," +
                    "       s.category AS category," +
                    "       s.address AS address," +
                    "       ST_Distance_Sphere(point(s.longitude, s.latitude), point(:longitude, :latitude)) AS distance" +
                    "  FROM store s" +
                    "  LEFT JOIN pick p ON s.store_id = p.store_id AND p.user_id = :userId" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.name LIKE :name" +
                    " ORDER BY distance IS NULL, distance",
            countQuery =
                    "SELECT" +
                    "       count(s.store_id) AS totalElements" +
                    "  FROM store s" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.name LIKE :name",
            nativeQuery = true)
    Page<StoresDto> findAllContainsName(@Param("userId") Long userId,
                                        @Param("longitude") Double longitude,
                                        @Param("latitude") Double latitude,
                                        @Param("name") String name,
                                        Pageable pageable);

    @Query(
            value =
                    "SELECT" +
                    "       s.store_id AS storeId," +
                    "       s.name AS storeName," +
                    "       s.description AS description," +
                    "       CASE WHEN p.pick_id IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END AS isPicked," +
                    "       s.category AS category," +
                    "       s.address AS address," +
                    "       ST_Distance_Sphere(point(s.longitude, s.latitude), point(:longitude, :latitude)) AS distance" +
                    "  FROM store s" +
                    "  LEFT JOIN pick p ON s.store_id = p.store_id AND p.user_id = :userId" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.name LIKE :name AND s.category = :category" +
                    " ORDER BY distance IS NULL, distance",
            countQuery =
                    "SELECT" +
                    "       count(s.store_id) AS totalElements" +
                    "  FROM store s" +
                    "  LEFT JOIN contract c ON s.store_id = c.store_id" +
                    " WHERE c.contract_id IS NULL AND s.name LIKE :name AND s.category = :category",
            nativeQuery = true)
    Page<StoresDto> findAllContainsNameAndCategory(@Param("userId") Long userId,
                                                   @Param("longitude") Double longitude,
                                                   @Param("latitude") Double latitude,
                                                   @Param("name") String name,
                                                   @Param("category") String category,
                                                   Pageable pageable);

}
