package com.backend.domain.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessHour {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessHourId;

    @Enumerated(EnumType.STRING)
    private BusinessDay dayOfWeek;

    private String openingTime;

    private String closingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public BusinessHour(BusinessDay dayOfWeek, String openingTime, String closingTime) {
        this.dayOfWeek = dayOfWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
