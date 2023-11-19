package com.backend.domain.store.dto;

import com.backend.domain.store.entity.BusinessDay;
import com.backend.domain.store.entity.BusinessHour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusinessHourDto {

    private String dayOfWeek;

    private HoursInfoDto hoursInfo;

    public BusinessHour toEntity() {
        return BusinessHour.builder()
                .dayOfWeek(BusinessDay.convert(dayOfWeek))
                .openingTime(hoursInfo.getOpeningTime())
                .closingTime(hoursInfo.getClosingTime())
                .build();
    }

}
