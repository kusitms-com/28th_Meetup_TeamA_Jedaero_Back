package com.backend.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    ALREADY_LOGOUT_MEMBER(BAD_REQUEST, "이미 로그아웃한 회원입니다."),
    ALREADY_EXIST_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다."),
    INVALID_TOKEN(UNAUTHORIZED, "잘못된 토큰입니다."),
    INVALID_GROUP_TYPE(BAD_REQUEST, "잘못된 그룹 종류입니다."),
    INVALID_PASSWORD(BAD_REQUEST, "잘못된 비밀번호입니다."),
    IMAGE_UPLOAD_FAIL(BAD_REQUEST, "이미지 업로드에 실패했습니다."),
    INVALID_FILE(BAD_REQUEST, "잘못된 파일 형식입니다."),
    TOO_MANY_SMS(TOO_MANY_REQUESTS, "짧은 시간에 너무 많은 요청을 보냈습니다. 1분 후에 재시도하세요."),
    NOT_EXISTS_POPUP_DATE(NOT_FOUND, "존재하지 않는 팝업 기간입니다."),
    STORE_NOT_FOUND(NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
    INVALID_DATE(BAD_REQUEST, "잘못된 날짜 형식입니다."),
    POPUP_NOT_FOUND(NOT_FOUND, "팝업이 존재하지 않습니다."),
    INVALID_POPUP(BAD_REQUEST, "잘못된 팝업 요청입니다.");

    private final int code;
    private final String message;

    ErrorCode(HttpStatus code, String message) {
        this.code = code.value();
        this.message = message;
    }
}