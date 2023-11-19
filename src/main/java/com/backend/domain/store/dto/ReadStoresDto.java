package com.backend.domain.store.dto;

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
public class ReadStoresDto {

    private List<StoresDto> stores;

    private int pageNumber;

    private Long totalCount;

    private boolean hasNext;

    public static ReadStoresDto from(Page<StoresDto> stores) {
        return ReadStoresDto.builder()
                .stores(stores.getContent())
                .pageNumber(stores.getNumber())
                .totalCount(stores.getTotalElements())
                .hasNext(stores.hasNext())
                .build();
    }

}
