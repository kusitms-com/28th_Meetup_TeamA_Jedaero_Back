package com.backend.domain.store.dto;

import com.backend.domain.store.entity.Category;
import com.backend.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStoreDto {

    private String storeName;
    private String genre;
    private String phoneNumber;
    private String address;
    private LocationDto location;
    private List<CreateBusinessHourDto> businessHours = new ArrayList<>();
    private List<MenuDto> menu = new ArrayList<>();

    public Store toEntity(Category category) {
        return Store.builder()
                .name(storeName)
                .address(address)
                .description(getDescription())
                .latitude(getLatitude())
                .longitude(getLongitude())
                .category(category)
                .contact(phoneNumber)
                .phoneNumber(phoneNumber)
                .businessHours(
                        businessHours.stream()
                                .filter(Objects::nonNull)
                                .map(CreateBusinessHourDto::toEntity)
                                .toList()
                )
                .build();
    }

    public String getDescription() {
        if (genre != null) {
            return genre;
        }
        if (!menu.isEmpty()) {
            return menu.stream()
                    .map(MenuDto::getName)
                    .limit(3)
                    .collect(Collectors.joining(", "));
        }
        return null;
    }


    public Double getLongitude() {
        return location == null ? null : location.getLongitude();
    }

    public Double getLatitude() {
        return location == null ? null : location.getLatitude();
    }

}
