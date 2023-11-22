package com.backend.domain.event.dto;

import com.backend.domain.event.entity.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadEventsRequest {

    @Schema(example = "COUPON")
    private EventType type;

    @Schema(example = "40")
    private int pageSize;

    @Schema(example = "0")
    private int pageNumber;

    {
        pageSize = 40;
        pageNumber = 0;
        type = EventType.COUPON;
    }

    @Schema(hidden = true)
    public PageRequest getPage() {
        return PageRequest.of(pageNumber, pageSize);
    }

}
