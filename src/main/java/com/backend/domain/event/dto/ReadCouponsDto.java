package com.backend.domain.event.dto;

import com.backend.domain.event.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadCouponsDto {

    private List<ReadCouponDto> coupons;

    private int pageNumber;

    private Long totalCount;

    private int totalPages;

    public static ReadCouponsDto from(Page<Event> coupons) {
        return ReadCouponsDto.builder()
                .pageNumber(coupons.getNumber())
                .totalCount(coupons.getTotalElements())
                .totalPages(coupons.getTotalPages())
                .coupons(getReadCouponDtos(coupons))
                .build();
    }

    private static List<ReadCouponDto> getReadCouponDtos(Page<Event> coupons) {
        return coupons.getContent().stream()
                .map(ReadCouponDto::from)
                .toList();
    }

}
