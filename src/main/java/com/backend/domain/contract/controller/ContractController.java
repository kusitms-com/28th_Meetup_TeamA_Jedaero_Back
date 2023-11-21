package com.backend.domain.contract.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.contract.dto.CreateContractRequest;
import com.backend.domain.contract.dto.ReadContractDetailsDto;
import com.backend.domain.contract.dto.ReadContractsDto;
import com.backend.domain.contract.service.ContractService;
import com.backend.domain.store.dto.ReadRequest;
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
@RequestMapping("/contract")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "제휴", description = "제휴 관련 API")
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    @Operation(
            summary = "제휴 등록",
            description =
                    "<h3>Request Body</h3>" +
                    "<p>storeId : 제휴 맺는 가게 아이디</p>" +
                    "<p>benefits : 혜택에 대한 정보를 담은 객체의 리스트(1개의 혜택이어도 리스트에 담아야함)</p>" +
                    "<p>startDate : 제휴가 시작되는 날짜를 입력 \"yyyy-MM-dd\" 형태로 입력</p>" +
                    "<p>endDate : 제휴가 끝나는 날짜 입력 \"yyyy-MM-dd\" 형태로 입력</p>" +
                    "<p>type : 제휴의 타입 FIX, RATE, MENU를 사용</p>" +
                    "<p>amount : 숫자를 입력, 20% -> 20을 입력, 5000원 -> 5000을 입력, 콜라 서비스 -> 1500을 입력한다(콜라가격)</p>" +
                    "<p>content : 혜택을 위한 조건</p>",
            responses = {
                    @ApiResponse(responseCode = "201")})
    public ResponseEntity<Void> createContract(@Parameter(hidden = true) @Login LoginUser loginUser, @RequestBody CreateContractRequest request) {
        contractService.createContract(loginUser, request);
        return ResponseDto.created();
    }

    @GetMapping
    @Operation(
            summary = "제휴한 가게 목록 조회",
            description =
                    "<h3>Request Parameter</h3>" +
                    "<p>isPicked : 사용X, true이든 false이든 결과가 달라지지 않음</p>" +
                    "<p>name : 검색할 이름 입력</p>" +
                    "<p>category : 카테고리  [FOOD, CAFE, BEAUTY, CULTURE, ETC, NONE] 중 하나 입력, 입력하지 않으면 NONE(전체카테고리)값으로 처리한다.</p>" +
                    "<p>pageSize : 한번에 불러오는 데이터의 양, 정수값 입력</p>" +
                    "<p>pageNumber : 정렬 후 몇번째 페이지를 불러올지 입력</p>" +
                    "<h3>Response</h3>" +
                    "<p>contractedStores : 제휴된 가게의 정보가 들어있는 리스트</p>" +
                    "<p>pageNumber : 정렬 후 페이지 번호(request parameter에서의 값과 일치)</p>" +
                    "<p>totalCount : 조건에 만족하는 총 가게 수</p>" +
                    "<p>hasNext : 다음 페이지가 존재하는지(더 불러올 데이터가 남아있는지 여부)</p>" +
                    "<p>storeId : 제휴 맺은 가게 id</p>" +
                    "<p>storeName : 제휴 맺은 가게 이름</p>" +
                    "<p>category : 제휴 맺은 가게 카테고리</p>" +
                    "<p>benefitId : 제휴 맺은 가게 재휴 혜택</p>" +
                    "<p>type : 제휴 혜택 타입 [FIX, RATE, MENU] 중 하나</p>" +
                    "<p>amount : 혜택의  크기</p>" +
                    "<p>content : 혜택의 조건</p>",
            responses = {
                    @ApiResponse(
                            responseCode = "200")})
    public ResponseEntity<ReadContractsDto> readContracts(@Parameter(hidden = true) @Login LoginUser loginUser, @ModelAttribute ReadRequest request) {
        return ResponseDto.ok(contractService.readContracts(loginUser, request));
    }

    @GetMapping("/details/{storeId}")
    @Operation(
            summary = "제휴 가게 상세 조회",
            description =
                    "<h3>Request Parameter</h3>" +
                    "<p>storeId : 상세 조회할 가게의 id</p>" +
                    "<h3>Response</h3>" +
                    "<p>storeName : 해당 가게 이름</p>" +
                    "<p>category : 카테고리  [FOOD, CAFE, BEAUTY, CULTURE, ETC, NONE] 속하는 값</p>" +
                    "<p>phoneNumber : 가게 연락처</p>" +
                    "<p>manager : 대학 내 담당자(학생회 이름으로 설정됨)</p>" +
                    "<p>mapUrl : 지도 url</p>" +
                    "<p>startDate : 제휴 시작 날짜</p>" +
                    "<p>endDate : 제휴 종료 날짜</p>" +
                    "<p>latitude : 위도</p>" +
                    "<p>longitude : 경도</p>" +
                    "<p>benefits : 혜택 정보의 리스트</p>" +
                    "<p>benefitId : 제휴 맺은 가게 재휴 혜택</p>" +
                    "<p>type : 제휴 혜택 타입 [FIX, RATE, MENU] 중 하나</p>" +
                    "<p>amount : 혜택의  크기</p>" +
                    "<p>content : 혜택의 조건</p>" +
                    "<p>visitInfo : 대시보드 정보를 담은 객체</p>" +
                    "<p>visits : 일정 기간 동안 방문 객 수</p>" +
                    "<p>totalBenefitAmount : 일정 기간 동안 고객이 받은 혜택의 총 양</p>" +
                    "<p>achievementRate : 목표 달성률</p>",
            responses = {
                    @ApiResponse(
                            responseCode = "200")})
    public ResponseEntity<ReadContractDetailsDto> readContractDetails(@Parameter(hidden = true) @Login LoginUser loginUser, @Parameter(name = "storeId", example = "2") @PathVariable Long storeId) {
        return ResponseDto.ok(contractService.readContractDetails(loginUser, storeId));
    }

}
