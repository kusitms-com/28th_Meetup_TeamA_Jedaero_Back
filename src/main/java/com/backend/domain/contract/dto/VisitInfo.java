package com.backend.domain.contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitInfo {

    private int visits;

    private int totalBenefitAmount;

    private double achievementRate;

}
