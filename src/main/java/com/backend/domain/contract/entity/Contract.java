package com.backend.domain.contract.entity;

import com.backend.domain.benefit.entity.Benefit;
import com.backend.common.domain.BaseEntity;
import com.backend.domain.store.entity.Store;
import com.backend.domain.university.entity.University;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "contract")
public class Contract extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String manager;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.PERSIST)
    private List<Benefit> benefits = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

}
