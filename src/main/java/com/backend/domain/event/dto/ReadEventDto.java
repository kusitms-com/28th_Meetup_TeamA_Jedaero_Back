package com.backend.domain.event.dto;

import com.backend.domain.event.entity.Condition;
import com.backend.domain.event.entity.Event;
import com.backend.domain.event.entity.EventType;
import com.backend.domain.store.dto.StoreDto;
import com.backend.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadEventDto {

    private StoreDto store;

    private Long eventId;

    private EventType eventType;

    private String eventName;

    private List<String> eventCondition;

    private int eventQuantity;

    public static ReadEventDto from(Event event, Store store) {
        return ReadEventDto.builder()
                .eventId(event.getEventId())
                .eventType(event.getType())
                .eventName(event.getName())
                .eventQuantity(event.getQuantity())
                .store(StoreDto.from(store))
                .eventCondition(getConditionStrings(event))
                .build();
    }

    private static List<String> getConditionStrings(Event event) {
        return event.getConditions().stream()
                .map(Condition::getContent)
                .toList();
    }

}
