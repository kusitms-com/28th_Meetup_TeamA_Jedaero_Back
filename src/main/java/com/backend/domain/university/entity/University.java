package com.backend.domain.university.entity;

import com.backend.domain.contract.entity.Contract;
import com.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class University {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long universityId;

    private String name;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "university")
    private List<Contract> contracts = new ArrayList<>();

    @OneToOne(mappedBy = "university")
    private User user;

}
