package com.backend.domain.contract.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.contract.dto.CreateContractRequest;
import com.backend.domain.contract.service.ContractService;
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

}
