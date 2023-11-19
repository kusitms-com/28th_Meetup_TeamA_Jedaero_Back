package com.backend.domain.benefit.entity;

import com.backend.common.domain.BaseEntity;
import com.backend.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "benefit")
public class Benefit extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long benefitId;

    @Enumerated(EnumType.STRING)
    private BenefitType type;

    private int amount;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

}
