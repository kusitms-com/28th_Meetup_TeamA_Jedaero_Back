package com.backend.domain.store.dto;

import com.backend.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

    private Long storeId;

    private String storeName;

    public static StoreDto from(Store store) {
        return StoreDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getName())
                .build();
    }

}
