package com.backend.domain.contract.dto;

import com.backend.domain.benefit.entity.Benefit;
import com.backend.domain.benefit.entity.BenefitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBenefitRequest {

    @Schema(name = "type", example = "RATE")
    private BenefitType type;

    @Schema(description = "할인 받는 양(int형)", example = "20")
    private int amount;

    @Schema(description = "조건 작성", example = "메인메뉴 구입시")
    private String condition;

    private String menu;

    public Benefit toEntity() {
        validateType();
        return Benefit.builder()
                .type(type)
                .amount(amount)
                .menu(menu)
                .conditions(condition)
                .build();
    }

    public void validateType() {
        if (type.equals(BenefitType.MENU)) {
            if (menu == null) {
                throw new RuntimeException();
            }
        } else {
            if (menu != null) {
                menu = null;
            }
        }
    }

}
