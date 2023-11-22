package com.backend.domain.benefit.entity;

import com.backend.common.domain.BaseEntity;
import com.backend.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "benefit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Benefit extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long benefitId;

    @Enumerated(EnumType.STRING)
    private BenefitType type;

    private int amount;

    private String menu;

    private String conditions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Builder
    public Benefit(BenefitType type, int amount, String conditions, String menu) {
        this.type = type;
        this.amount = amount;
        this.conditions = conditions;
        this.menu = menu;
    }

    public void add(Contract contract) {
        this.contract = contract;
    }

    public void expire() {
        contract = null;
    }

}
