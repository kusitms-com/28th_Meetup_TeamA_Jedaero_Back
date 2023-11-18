package com.backend.domain.store.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.store.dto.*;
import com.backend.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Tag(name = "가게", description = "가게 관련 API")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Void> createStore(@RequestBody CreateStoreRequest request) {
        storeService.createStore(request);
        return ResponseDto.created();
    }

    // TODO useremail을 바탕으로 university의 longitude, latitude 정보를 가져와서 이용한다.
    @GetMapping("/details/{storeId}")
    @Operation(
            summary = "가게 상세 정보 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ReadStoreDetailsDto.class)))
            })
    public ResponseEntity<ReadStoreDetailsDto> readStoreDetails(@Login LoginUser loginUser, @PathVariable Long storeId) {
        return ResponseDto.ok(storeService.readStoreDetails(loginUser, storeId));
    }

    @GetMapping("/search")
    @Operation(
            summary = "가게 목록 검색 및 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ReadStoresDto.class)))
            })
    public ResponseEntity<ReadStoresDto> readStores(@Login LoginUser loginUser, @ModelAttribute ReadStoresRequest request) {
        return ResponseDto.ok(storeService.readStores(loginUser, request));
    }

    @PostMapping("/pick")
    @Operation(
            summary = "픽 등록",
            responses = {
                    @ApiResponse(responseCode = "201")
            })
    public ResponseEntity<Void> createPick(@Login LoginUser loginUser, @RequestBody PickRequest request) {
        storeService.createPick(loginUser, request);
        return ResponseDto.created();
    }

    @DeleteMapping("/pick")
    @Operation(
            summary = "픽 취소",
            responses = {
                    @ApiResponse(responseCode = "200")
            })
    public ResponseEntity<Void> deletePick(@Login LoginUser loginUser, @RequestBody PickRequest request) {
        storeService.deletePick(loginUser, request);
        return ResponseDto.ok();
    }

}
