package com.backend.domain.event.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "conditions")
public class Condition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public Condition(String content) {
        this.content = content;
    }

    public void belong(Event event) {
        this.event = event;
    }

    public void delete() {
        event = null;
    }
}
