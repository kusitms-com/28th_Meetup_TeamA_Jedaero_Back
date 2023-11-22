package com.backend.domain.event.entity;

import com.backend.common.domain.BaseEntity;
import com.backend.domain.benefit.entity.BenefitType;
import com.backend.domain.contract.entity.Contract;
import com.backend.domain.event.dto.UpdateEventRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String name;

    @Enumerated(EnumType.STRING)
    private BenefitType type;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Condition> conditions = new ArrayList<>();

    private int discount;

    private int quantity;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Builder
    public Event(BenefitType type, String name, int discount, int quantity, LocalDate startDate, LocalDate endDate, Contract contract) {
        this.type = type;
        this.name = name;
        this.discount = discount;
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contract = contract;
    }

    public void add(Contract contract) {
        this.contract = contract;
        setDate(contract);
    }

    public void add(Condition condition) {
        this.conditions.add(condition);
        condition.belong(this);
    }

    public void addAll(List<Condition> conditions) {
        conditions.forEach(this::add);
    }

    public void setDate(Contract contract) {
        if (type.equals(BenefitType.COUPON)) {
            this.startDate = contract.getStartDate();
            this.endDate = contract.getEndDate();
        }
    }

    public void expire() {
        this.endDate = LocalDate.now().minusDays(1);
    }

    public void update(UpdateEventRequest request) {
        name = request.getName();
        discount = request.getDiscount();
        quantity = request.getQuantity();
        if (type.equals(BenefitType.STAMP)) {
            startDate = request.getStartDate();
            endDate = startDate.plusDays(request.getDuration().getPlusDate());
        }
        deleteConditions();
        request.getConditions().stream()
                .map(Condition::new)
                .forEach(this::add);
    }

    private void delete(Condition condition) {
        conditions.remove(condition);
        condition.delete();
    }

    private void deleteConditions() {
        for (int i = conditions.size() - 1; i >= 0; i--) {
            delete(conditions.get(i));
        }
    }

}
