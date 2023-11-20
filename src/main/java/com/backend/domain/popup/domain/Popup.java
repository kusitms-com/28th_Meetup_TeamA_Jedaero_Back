package com.backend.domain.popup.domain;

import com.backend.domain.contract.entity.Contract;
import com.backend.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "popup")
public class Popup {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "popup_id")
    private Long id;

    private String title;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;
}