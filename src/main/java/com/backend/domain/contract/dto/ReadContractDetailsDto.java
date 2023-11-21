package com.backend.domain.contract.dto;

import com.backend.domain.contract.entity.Contract;
import com.backend.domain.store.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadContractDetailsDto {

    private Long storeId;

    private String storeName;

    private Category category;

    private List<BenefitDto> benefits;

    private String phoneNumber;

    private String manager;

    private String mapUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double latitude;

    private Double longitude;

    private VisitInfo visitInfo;

    public static ReadContractDetailsDto from(Contract contract) {
        // TODO VisitInfo 추가하여 완성할 것
        return ReadContractDetailsDto.builder()
                .storeId(contract.getStore().getStoreId())
                .storeName(contract.getStore().getName())
                .category(contract.getStore().getCategory())
                .phoneNumber(contract.getStore().getPhoneNumber())
                .manager(contract.getManager())
                .mapUrl(contract.getStore().getMapUrl())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .latitude(contract.getStore().getLatitude())
                .longitude(contract.getStore().getLongitude())
                .benefits(getBenefitDtos(contract))
                .build();
    }

    private static List<BenefitDto> getBenefitDtos(Contract contract) {
        return contract.getBenefits().stream()
                .map(BenefitDto::from)
                .toList();
    }

}
