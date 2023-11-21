package com.backend.domain.popup.dto.request;

import com.backend.domain.contract.entity.Contract;
import com.backend.domain.popup.domain.Popup;
import com.backend.domain.store.entity.Store;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PopupCreateRequest(@NotBlank String title, @NotBlank String content, @NotBlank String endDate,
                                 @NotBlank String reservation, Long storeId) {
    public Popup toEntity(LocalDate endDate, LocalDateTime reservation, Store store, Contract contract) {
        return Popup.builder()
                .title(title)
                .content(content)
                .startDate(LocalDate.now())
                .endDate(endDate)
                .reservation(reservation)
                .store(store)
                .contract(contract)
                .build();
    }
}