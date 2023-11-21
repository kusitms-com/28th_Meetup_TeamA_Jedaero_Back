package com.backend.domain.contract.dto;

import com.backend.domain.benefit.entity.Benefit;
import com.backend.domain.benefit.entity.BenefitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDto {

    private Long benefitId;

    private BenefitType type;

    private int amount;

    private String content;

    public static BenefitDto from(Benefit benefit) {
        return BenefitDto.builder()
                .benefitId(benefit.getBenefitId())
                .type(benefit.getType())
                .amount(benefit.getAmount())
                .content(benefit.getContent())
                .build();
    }

}
