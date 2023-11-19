package com.backend.domain.store.dto;

import com.backend.domain.store.entity.BusinessHour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessHourDto {

    private String dayOfWeek;

    private String openingTime;

    private String closingTime;

    public static BusinessHourDto from(BusinessHour businessHour) {
        return BusinessHourDto.builder()
                .dayOfWeek(businessHour.getDayOfWeek().getDayOfWeek())
                .openingTime(businessHour.getOpeningTime())
                .closingTime(businessHour.getClosingTime())
                .build();
    }

}
