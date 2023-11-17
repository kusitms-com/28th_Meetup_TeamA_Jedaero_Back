package com.backend.domain.store.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.store.dto.*;
import com.backend.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Void> createStore(@RequestBody CreateStoreRequest request) {
        storeService.createStore(request);
        return ResponseDto.created();
    }

    // TODO useremail을 바탕으로 university의 longitude, latitude 정보를 가져와서 이용한다.
    @GetMapping("/details/{storeId}")
    public ResponseEntity<ReadStoreDetailsDto> readStoreDetails(@Login LoginUser loginUser, @PathVariable Long storeId) {
        return ResponseDto.ok(storeService.readStoreDetails(loginUser, storeId));
    }

    @PostMapping("/pick")
    public ResponseEntity<Void> createPick(@Login LoginUser loginUser, @RequestBody CreatePickRequest request) {
        storeService.createPick(loginUser, request);
        return ResponseDto.created();
    }

}
