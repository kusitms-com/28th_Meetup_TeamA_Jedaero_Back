package com.backend.domain.popup.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.popup.dto.request.PopupCreateRequest;
import com.backend.domain.popup.service.PopupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popups")
public class PopupController {

    private final PopupService popupService;

    @PostMapping
    public ResponseEntity<String> createPopup(@Login LoginUser loginUser,
                                              @Valid @RequestBody PopupCreateRequest popupCreateRequest) {
        popupService.createPopup(loginUser, popupCreateRequest);
        return ResponseDto.created("팝업 생성 성공");
    }

    @DeleteMapping("/{popupId}")
    public ResponseEntity<String> deletePopup(@Login LoginUser loginUser, @PathVariable Long popupId) {
        popupService.deletePopup(loginUser, popupId);
        return ResponseDto.ok("팝업 삭제 성공");
    }
}