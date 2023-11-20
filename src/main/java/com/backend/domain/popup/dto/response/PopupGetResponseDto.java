package com.backend.domain.popup.dto.response;

import com.backend.domain.popup.domain.EndDateType;
import com.backend.domain.popup.domain.Popup;
import com.backend.domain.popup.domain.ReservationType;
import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@Slf4j
public record PopupGetResponseDto(String content, String endDate, String reservation) {

    public static PopupGetResponseDto from(Popup popup) {
        return new PopupGetResponseDto(popup.getContent(), getEndDate(popup).getDateType(),
                getReservation(popup).getReservationType());
    }

    private static EndDateType getEndDate(Popup popup) {
        long dayDifference = ChronoUnit.DAYS.between(popup.getStartDate(), popup.getEndDate());
        log.info("날짜 = {} {}", popup.getEndDate(), popup.getStartDate());
        log.info("날짜 차이 = {}", dayDifference);
        return Stream.of(EndDateType.values())
                .filter(endDateType -> endDateType.getPlusDate() == dayDifference)
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_POPUP_DATE));
    }

    private static ReservationType getReservation(Popup popup) {
        LocalDate reservationDate = popup.getReservation().toLocalDate();
        LocalDate startDate = popup.getStartDate();

        if (reservationDate.equals(startDate)) {
            return ReservationType.NOW;
        }

        return ReservationType.RESERVATION;
    }
}