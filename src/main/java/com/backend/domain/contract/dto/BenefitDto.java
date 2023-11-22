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

    private String condition;

    private String content;

    public static BenefitDto from(Benefit benefit) {
        return BenefitDto.builder()
                .benefitId(benefit.getBenefitId())
                .content(createContent(benefit))
                .condition(benefit.getConditions())
                .build();
    }

    public static String createContent(Benefit benefit) {
        if (benefit.getType().equals(BenefitType.FIX)) {
            return benefit.getAmount() + "원 할인";
        }
        if (benefit.getType().equals(BenefitType.RATE)) {
            return benefit.getAmount() + "% 할인";
        }
        if (benefit.getType().equals(BenefitType.MENU)) {
            return benefit.getMenu() + " 무료 제공";
        }
        throw new RuntimeException();
    }

}
