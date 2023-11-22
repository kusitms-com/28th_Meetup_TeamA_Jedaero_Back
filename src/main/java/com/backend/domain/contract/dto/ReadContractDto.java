package com.backend.domain.contract.dto;

import com.backend.domain.contract.entity.Contract;
import com.backend.domain.store.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadContractDto {

    private Long storeId;

    private String storeName;

    private Category category;

    private String address;

    private List<BenefitDto> benefits;

    public static ReadContractDto from(Contract contract) {
        return ReadContractDto.builder()
                .storeId(contract.getStore().getStoreId())
                .storeName(contract.getStore().getName())
                .category(contract.getStore().getCategory())
                .address(contract.getStore().getAddress())
                .benefits(createBenefitDtos(contract))
                .build();
    }

    private static List<BenefitDto> createBenefitDtos(Contract contract) {
        return contract.getBenefits().stream()
                .map(BenefitDto::from)
                .toList();
    }

}
