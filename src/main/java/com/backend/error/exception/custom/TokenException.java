package com.backend.error.exception.custom;

import com.backend.error.ErrorCode;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {

    private final int code;

    public TokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}