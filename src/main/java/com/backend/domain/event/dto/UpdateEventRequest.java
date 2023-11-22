package com.backend.domain.event.dto;

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
public class UpdateEventRequest {

    @Schema(example = "3")
    private Long eventId;

    @Schema(example = "5000원 할인 쿠폰")
    private String name;

    private List<String> conditions;

    private int discount;

    @Schema(example = "50")
    private int quantity;

    @Schema(example = "2023-11-22")
    private LocalDate startDate;

    @Schema(example = "일주일간")
    private EndDateType duration;

}
