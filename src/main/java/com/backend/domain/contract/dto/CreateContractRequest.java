package com.backend.domain.contract.dto;

import com.backend.domain.benefit.entity.Benefit;
import com.backend.domain.contract.entity.Contract;
import com.backend.domain.store.entity.Store;
import com.backend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateContractRequest {

    @Schema(name = "storeId", example = "5")
    private Long storeId;

    @Schema(name = "benefits")
    private List<CreateBenefitRequest> benefits;

    @Schema(name = "startDate", example = "2023-11-12")
    private LocalDate startDate;

    @Schema(name = "endDate", example = "2024-11-12")
    private LocalDate endDate;

    public Contract toEntity(User user, Store store) {
        return Contract.builder()
                .startDate(createStartDate())
                .endDate(endDate)
                .manager(user.getTypeName())
                .university(user.getUniversity())
                .store(store)
                .benefits(createBenefits())
                .build();
    }

    public LocalDate createStartDate() {
        if (startDate == null) {
            return LocalDate.now();
        }
        return startDate;
    }

    public List<Benefit> createBenefits() {
        return this.benefits.stream()
                .map(CreateBenefitRequest::toEntity)
                .toList();
    }

}
