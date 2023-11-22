package com.backend.domain.event.dto;

import com.backend.domain.event.entity.Condition;
import com.backend.domain.event.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadCouponDto {

    private Long couponStoreId;

    private String couponStore;

    private Long couponId;

    private String couponName;

    private List<String> couponCondition;

    private int couponQuantity;

    public static ReadCouponDto from(Event coupon) {
        return ReadCouponDto.builder()
                .couponStoreId(coupon.getContract().getStore().getStoreId())
                .couponStore(coupon.getContract().getStore().getName())
                .couponId(coupon.getEventId())
                .couponName(coupon.getName())
                .couponQuantity(coupon.getQuantity())
                .couponCondition(getConditionStrings(coupon))
                .build();
    }

    private static List<String> getConditionStrings(Event coupon) {
        return coupon.getConditions().stream()
                .map(Condition::getContent)
                .toList();
    }

}
