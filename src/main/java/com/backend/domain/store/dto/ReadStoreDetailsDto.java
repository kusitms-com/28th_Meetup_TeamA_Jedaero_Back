package com.backend.domain.store.dto;

import com.backend.domain.store.entity.BusinessHour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadStoreDetailsDto {

    private Long storeId;

    private String storeName;

    private String category;

    private String description;

    private String address;

    private List<BusinessHourDto> businessHours;

    private String phoneNumber;

    private Double distance;

    private String mapUrl;

    private boolean isPicked;

    public static ReadStoreDetailsDto from(StoreDetailsDto store, List<BusinessHour> businessHours) {
        return ReadStoreDetailsDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .category(store.getCategory().getCategoryName())
                .description(store.getDescription())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .distance(store.getDistance())
                .mapUrl(store.getMapUrl())
                .isPicked(store.getIsPicked())
                .businessHours(businessHours.stream()
                        .map(BusinessHourDto::from)
                        .toList())
                .build();
    }

}
