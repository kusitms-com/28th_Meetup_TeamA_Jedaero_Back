package com.backend.domain.event.dto;

import com.backend.domain.event.entity.Condition;
import com.backend.domain.event.entity.Event;
import com.backend.domain.event.entity.EventType;
import com.backend.domain.popup.domain.EndDateType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private Long storeId;

    @Schema(example = "COUPON")
    @NotEmpty
    private EventType type;

    @Schema(example = "5000원 할인 쿠폰")
    @NotEmpty
    private String name;

    private List<String> conditions;

    private int discount;

    @Schema(example = "50")
    private int quantity;

    @Schema(example = "2023-11-22")
    private LocalDate startDate;

    @Schema(example = "일주일간")
    private EndDateType duration;

    {
        startDate = LocalDate.now();
    }

    public Event toEntity() {
        validateDuration();
        validateQuantity();
        Event event = Event.builder()
                .type(type)
                .name(name)
                .quantity(quantity)
                .discount(createDiscount())
                .startDate(startDate)
                .endDate(createEndDate())
                .build();
        event.addAll(createConditions());
        return event;
    }

    private void validateDuration() {
        if (type.equals(EventType.STAMP)) {
            if (duration == null) {
                throw new RuntimeException();
            }
        }
    }

    public void validateQuantity() {
        if (quantity <= 0 && quantity != -1) {
            throw new RuntimeException();
        }
    }

    private int createDiscount() {
        if (type.equals(EventType.STAMP)) {
            return 0;
        }
        return discount;
    }

    private List<Condition> createConditions() {
        return conditions.stream()
                .map(Condition::new)
                .toList();
    }

    private LocalDate createEndDate() {
        if (duration == null) {
            return startDate;
        }
        return startDate.plusDays(duration.getPlusDate());
    }

}
