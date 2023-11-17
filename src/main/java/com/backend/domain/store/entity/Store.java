package com.backend.domain.store.entity;

import com.backend.common.domain.BaseEntity;
import com.backend.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private String name;

    private String address;

    private String description;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String contact;

    private String mapUrl;

    private String phoneNumber;

    @OneToMany(mappedBy = "store", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BusinessHour> businessHours = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Contract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Pick> picks = new ArrayList<>();

}
