package com.backend.domain.event.dto;

import com.backend.domain.benefit.entity.BenefitType;
import com.backend.domain.event.entity.Condition;
import com.backend.domain.event.entity.Event;
import com.backend.domain.popup.domain.EndDateType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {

    @Schema(example = "3")
    private Long storeId;

    @Schema(example = "COUPON")
    private BenefitType type;

    @Schema(example = "5000원 할인 쿠폰")
    private String name;

    private List<String> conditions;

    @Schema(example = "50")
    private int quantity;

    @Schema(example = "2023-11-22")
    private LocalDate startDate;

    @Schema(example = "일주일간")
    private EndDateType duration;

    public Event toEntity() {
        Event event = Event.builder()
                .type(type)
                .name(name)
                .quantity(quantity)
                .startDate(startDate)
                .endDate(startDate.plusDays(duration.getPlusDate()))
                .build();
        event.addAll(createConditions());
        return event;
    }

    private List<Condition> createConditions() {
        return conditions.stream()
                .map(Condition::new)
                .toList();
    }

}
