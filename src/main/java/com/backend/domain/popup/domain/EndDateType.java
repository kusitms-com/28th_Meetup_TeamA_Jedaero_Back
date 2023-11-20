package com.backend.domain.popup.domain;

import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum EndDateType {
    ONE_DAY("하루", 1),
    ONE_WEEK("일주일간", 7),
    TWO_WEEK("2주간", 14),
    ONE_MONTH("한달간", 30),
    NO_LIMIT("제한 없음", 100_000);

    private final String DateType;

    private final int plusDate;

    EndDateType(String dateType, int plusDate) {
        DateType = dateType;
        this.plusDate = plusDate;
    }

    @JsonCreator
    public static EndDateType create(String request) {
        return Stream.of(values())
                .filter(value -> value.DateType.equalsIgnoreCase(request))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_POPUP_DATE));
    }
}