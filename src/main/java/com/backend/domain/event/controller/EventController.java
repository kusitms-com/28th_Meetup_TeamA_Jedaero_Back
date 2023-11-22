package com.backend.domain.event.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.event.dto.CreateEventRequest;
import com.backend.domain.event.dto.ReadCouponsDto;
import com.backend.domain.event.dto.ReadEventsDto;
import com.backend.domain.event.dto.ReadEventsRequest;
import com.backend.domain.event.service.EventService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "이벤트", description = "이벤트 관리 API")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Void> createEvent(@Parameter(hidden = true) @Login LoginUser loginUser, @RequestBody CreateEventRequest request) {
        eventService.createEvent(loginUser, request);
        return ResponseDto.created();
    }

    @GetMapping("/coupon")
    public ResponseEntity<ReadCouponsDto> readCoupons(@Parameter(hidden = true) @Login LoginUser loginUser, @ModelAttribute ReadEventsRequest request) {
        ReadCouponsDto events = eventService.readCoupons(loginUser, request);
        return ResponseDto.ok(events);
    }

    @GetMapping
    public ResponseEntity<ReadEventsDto> readEvents(@Parameter(hidden = true) @Login LoginUser loginUser, @ModelAttribute ReadEventsRequest request) {
        ReadEventsDto events = eventService.readEvents(loginUser, request);
        return ResponseDto.ok(events);
    }

}
