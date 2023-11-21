package com.backend.domain.contract.dto;

import com.backend.domain.contract.entity.Contract;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadContractsDto {

    private List<ReadContractDto> contractedStores;

    private int pageNumber;

    private Long totalCount;

    private boolean hasNext;

    public static ReadContractsDto from(Page<Contract> contracts) {
        return ReadContractsDto.builder()
                .contractedStores(getContractDtos(contracts.getContent()))
                .pageNumber(contracts.getNumber())
                .totalCount(contracts.getTotalElements())
                .hasNext(contracts.hasNext())
                .build();
    }

    private static List<ReadContractDto> getContractDtos(List<Contract> contracts) {
        return contracts.stream()
                .map(ReadContractDto::from)
                .toList();
    }

}
