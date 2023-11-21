package com.backend.domain.store.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.store.dto.*;
import com.backend.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "가게", description = "가게 관련 API")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Void> createStore(@RequestBody CreateStoreRequest request) {
        storeService.createStore(request);
        return ResponseDto.created();
    }

        @GetMapping("/details/{storeId}")
        @Operation(
                summary = "가게 상세 정보 조회",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                content = @Content(schema = @Schema(implementation = ReadStoreDetailsDto.class)))
                })
        public ResponseEntity<ReadStoreDetailsDto> readStoreDetails(@Parameter(hidden = true) @Login LoginUser loginUser, @Parameter(name = "storeId") @PathVariable Long storeId) {
            return ResponseDto.ok(storeService.readStoreDetails(loginUser, storeId));
        }

    @GetMapping("/search")
    @Operation(
            summary = "가게 목록 검색 및 조회",
            description =
                    "<p>{url}/store/search?isPicked=true&name=건&category=FOOD&pageSize=40&pageNumber=0</p>" +
                    "<p>isPicked = true/false, default = false</p>" +
                    "<p>name = String 타입의 가게 명, 입력하지 않아도 사용 가능</p>" +
                    "<p>category = [FOOD, CAFE, BEAUTY, CULTURE, ETC, NONE] 중 하나, default = NONE(카테고리 검색X)</p>" +
                    "<p>pageSize = int 값, default = 40</p>" +
                    "<p>pageNumber = 0부터 int 값, default = 0</p>",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ReadStoresDto.class)))
            })
    public ResponseEntity<ReadStoresDto> readStores(@Parameter(hidden = true) @Login LoginUser loginUser, @ModelAttribute ReadRequest request) {
        return ResponseDto.ok(storeService.readStores(loginUser, request));
    }

    @PostMapping("/pick")
    @Operation(
            summary = "픽 등록",
            responses = {
                    @ApiResponse(responseCode = "201")
            })
    public ResponseEntity<Void> createPick(@Parameter(hidden = true) @Login LoginUser loginUser, @RequestBody PickRequest request) {
        storeService.createPick(loginUser, request);
        return ResponseDto.created();
    }

    @DeleteMapping("/pick")
    @Operation(
            summary = "픽 취소",
            responses = {
                    @ApiResponse(responseCode = "200")
            })
    public ResponseEntity<Void> deletePick(@Parameter(hidden = true) @Login LoginUser loginUser, @RequestBody PickRequest request) {
        storeService.deletePick(loginUser, request);
        return ResponseDto.ok();
    }

}
