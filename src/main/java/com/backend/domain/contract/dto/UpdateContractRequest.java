package com.backend.domain.contract.dto;

import com.backend.domain.benefit.entity.Benefit;
import com.backend.domain.contract.entity.Contract;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContractRequest {

    @Schema(name = "storeId", example = "5")
    private Long storeId;

    @Schema(name = "benefits")
    private List<UpdateBenefitRequest> benefits;

    @Schema(name = "startDate", example = "2023-11-12")
    private LocalDate startDate;

    @Schema(name = "endDate", example = "2024-11-12")
    private LocalDate endDate;

    @Schema(name = "manager", example = "숭실대 총학생회")
    private String manager;

    public List<Benefit> createBenefits(Contract contract) {
        return benefits.stream()
                .map(UpdateBenefitRequest::toEntity)
                .peek(b -> b.add(contract))
                .toList();
    }

}
