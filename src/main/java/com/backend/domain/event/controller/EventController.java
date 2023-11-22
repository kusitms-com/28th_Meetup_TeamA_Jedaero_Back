package com.backend.domain.event.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.event.dto.*;
import com.backend.domain.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "이벤트 등록",
            description =
                    "<p>쿠폰과 스탬프를 등록합니다</p>" +
                            "<p>쿠폰을 등록할 때에는 type을 COUPON으로 선택하고 startDate와 duration을 입력하지 않는다.</p>" +
                            "<p>스탬프를 등록할 때에는 type을 STAMP 선택하고 startDate와 duration을 입력한다. discount는 입력하지 않는다.</p>")
    public ResponseEntity<Void> createEvent(@Parameter(hidden = true) @Login LoginUser loginUser, @RequestBody CreateEventRequest request) {
        eventService.createEvent(loginUser, request);
        return ResponseDto.created();
    }

    @GetMapping("/coupon")
    @Operation(summary = "쿠폰 목록 조회")
    public ResponseEntity<ReadCouponsDto> readCoupons(@Parameter(hidden = true) @Login LoginUser loginUser, @ModelAttribute ReadEventsRequest request) {
        ReadCouponsDto events = eventService.readCoupons(loginUser, request);
        return ResponseDto.ok(events);
    }

    @GetMapping
    @Operation(summary = "이벤트 목록 조회")
    public ResponseEntity<ReadEventsDto> readEvents(@Parameter(hidden = true) @Login LoginUser loginUser, @ModelAttribute ReadEventsRequest request) {
        ReadEventsDto events = eventService.readEvents(loginUser, request);
        return ResponseDto.ok(events);
    }

    @PatchMapping
    @Operation(summary = "이벤트 수정")
    public ResponseEntity<Void> updateEvent(@Parameter(hidden = true) @Login LoginUser loginUser, @RequestBody UpdateEventRequest request) {
        eventService.updateEvent(loginUser, request);
        return ResponseDto.ok();
    }


    @DeleteMapping("/{eventId}")
    @Operation(summary = "이벤트 삭제")
    public ResponseEntity<Void> deleteEvent(@Parameter(hidden = true) @Login LoginUser loginUser, @Parameter(name = "eventId") @PathVariable Long eventId) {
        eventService.deleteEvent(loginUser, eventId);
        return ResponseDto.ok();
    }

}
