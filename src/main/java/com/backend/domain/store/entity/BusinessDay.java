package com.backend.domain.store.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BusinessDay {

    MONDAY(1, "월"),
    TUESDAY(2, "화"),
    WEDNESDAY(3, "수"),
    THURSDAY(4, "목"),
    FRIDAY(5, "금"),
    SATURDAY(6, "토"),
    SUNDAY(7, "일"),
    EVERYDAY(100, "매일"),
    NONE(101, "없음");

    private final int value;
    private final String dayOfWeek;

    BusinessDay(int value, String dayOfWeek) {
        this.value = value;
        this.dayOfWeek = dayOfWeek;
    }

    public static BusinessDay convert(String dayOfWeek) {
        return Arrays.stream(BusinessDay.values())
                .filter(b -> b.getDayOfWeek().equals(dayOfWeek))
                .findFirst()
                .orElse(BusinessDay.NONE);
    }

}
