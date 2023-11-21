package com.backend.domain.popup.controller;

import com.backend.common.dto.PageResponseDto;
import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.popup.dto.request.PopupCreateRequest;
import com.backend.domain.popup.dto.response.PopupGetResponseDto;
import com.backend.domain.popup.service.PopupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popups")
public class PopupController {

    private final PopupService popupService;

    @GetMapping("/{pageNumber}")
    @Operation(summary = "팝업 조회", description = "사용자가 작성한 팝업을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "팝업 리스트를 반환합니다.",
                            content = @Content(schema = @Schema(implementation = PageResponseDto.class)))
            })
    public ResponseEntity<PageResponseDto> getPopups(@Login @Schema(hidden = true) LoginUser loginUser, @PathVariable int pageNumber) {
        Page<PopupGetResponseDto> response = popupService.getPopups(loginUser, pageNumber);
        return PageResponseDto.of(response);
    }

    @PostMapping
    @Operation(summary = "팝업 생성", description = "팝업을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "생성 성공",
                            content = @Content(schema = @Schema(implementation = String.class)))
            })
    public ResponseEntity<String> createPopup(@Login @Schema(hidden = true) LoginUser loginUser,
                                              @Valid @RequestBody PopupCreateRequest popupCreateRequest) {
        popupService.createPopup(loginUser, popupCreateRequest);
        return ResponseDto.created("팝업 생성 성공");
    }

    @DeleteMapping("/{popupId}")
    @Operation(summary = "팝업 삭제", description = "팝업을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공",
                            content = @Content(schema = @Schema(implementation = String.class)))
            })
    public ResponseEntity<String> deletePopup(@Login @Schema(hidden = true) LoginUser loginUser, @PathVariable Long popupId) {
        popupService.deletePopup(loginUser, popupId);
        return ResponseDto.ok("팝업 삭제 성공");
    }
}