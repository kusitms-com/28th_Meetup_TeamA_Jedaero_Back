package com.backend.domain.event.dto;

import com.backend.domain.event.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadEventsDto {

    private List<ReadEventDto> events;

    private int pageNumber;

    private Long totalCount;

    private int totalPages;

    public static ReadEventsDto from(Page<Event> coupons) {
        return ReadEventsDto.builder()
                .pageNumber(coupons.getNumber())
                .totalCount(coupons.getTotalElements())
                .totalPages(coupons.getTotalPages())
                .events(getReadEventDtos(coupons))
                .build();
    }

    private static List<ReadEventDto> getReadEventDtos(Page<Event> events) {
        return events.getContent().stream()
                .map(event ->
                        ReadEventDto.from(event, event.getContract().getStore()))
                .toList();
    }

}
