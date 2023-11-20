package com.backend.domain.popup.domain;

import lombok.Getter;

@Getter
public enum ReservationType {
    NOW("실시간"),
    RESERVATION("예약");

    private final String reservationType;

    ReservationType(String reservationType) {
        this.reservationType = reservationType;
    }

    public static boolean isNow(String request) {
        return NOW.getReservationType().equals(request);
    }
}