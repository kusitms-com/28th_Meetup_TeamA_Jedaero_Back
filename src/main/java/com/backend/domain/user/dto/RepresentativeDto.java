package com.backend.domain.user.dto;

import com.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepresentativeDto {

    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    public static RepresentativeDto from(User user) {
        return RepresentativeDto.builder()
                .id(user.getId())
                .name(user.getTypeName())
                .latitude(user.getUniversity().getLatitude())
                .longitude(user.getUniversity().getLongitude())
                .build();
    }

}
